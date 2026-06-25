# Lab: Foutafhandeling – Ontwerp & Implementeer een ExceptionMapper

## Scenario

Het team heeft de afgelopen weken een bookings-API gebouwd. De happy path werkt goed, maar bij fouten geeft de API soms een kale `500` terug met een stacktrace in de response body. Een security review heeft dit als kritisch gemarkeerd: stacktraces mogen nooit naar de client lekken. Bovendien is het error-formaat inconsistent. Het ene endpoint retourneert `{ "error": "..." }`, een ander `{ "message": "..." }`. Dit breekt de frontend.

Jouw taak: ontwerp een consistente error-body en implementeer een centrale `ExceptionMapper` die alle booking-fouten afhandelt.

---

## Learning Goals

- Verschillende categorieën fouten (validatie, business logic, systeem) identificeren en de juiste statuscode toewijzen
- Een consistente error-body samenstellen met de vereiste velden
- Een `ExceptionMapper` implementeren in Jakarta REST met `@Provider` en de juiste generieke signatuur
- Redeneren over waarom stacktraces een beveiligingsrisico vormen en hoe je intern logt zonder informatie te lekken
- Een catch-all `Exception`-mapper toevoegen die de server beschermt tegen onverwachte fouten

---

## Prerequisites

- De theorie over foutafhandeling, error body-structuur en exception mapping is behandeld
- Basiskennis Jakarta REST: `@Provider`, `ExceptionMapper<T>`, `Response`
- Basiskennis Java logging (`java.util.logging.Logger` of vergelijkbaar)

---

# Lab Parts

Dit lab bevat **3 delen**.

---

## Part 1: Categoriseer de fouten

### What you will do

Hieronder staan vijf scenario's. Bepaal voor elk scenario:

1. Welke foutcategorie: **validatie**, **business logic**, of **systeem**?
2. Welke HTTP-statuscode?
3. Hoe ziet de error body eruit? Vul de velden `code`, `message` en (indien van toepassing) `details` in.

| #   | Scenario                                                                                     | Categorie | Statuscode | `code` | `message` |
| --- | -------------------------------------------------------------------------------------------- | --------- | ---------- | ------ | --------- |
| A   | Een gebruiker probeert een boek te reserveren, maar zijn account is geblokkeerd              |           |            |        |           |
| B   | `POST /users` waarbij het veld `email` ontbreekt in de JSON body                             |           |            |        |           |
| C   | Een request om een recensie te verwijderen, maar de database gooit een onverwachte exception |           |            |        |           |
| D   | `POST /orders` waarbij het product al uitverkocht is (voorraad = 0)                          |           |            |        |           |
| E   | `PATCH /users/42` met `{ "age": "drieëntwintig" }` (string in plaats van integer)            |           |            |        |           |

Discussievraag: scenario A en D zijn allebei business logic fouten. Toch hebben ze een andere statuscode. Waarom?

### Success criteria

- Alle vijf categorieën zijn correct
- Alle vijf statuscodes zijn correct
- De `code`-waarden zijn machine-leesbaar (hoofdletters met underscores, bijv. `ACCOUNT_BLOCKED`)
- Je kunt het verschil tussen scenario A en D uitleggen

### Hints

<details>
<summary>Hint 1</summary>

Validatiefouten gaan over de _vorm_ van de data: klopt de JSON, zijn verplichte velden aanwezig, hebben waarden het juiste type? Business logic fouten gaan over de _staat van de wereld_: de data is technisch correct, maar de actie is niet toegestaan.

</details>

<details>
<summary>Hint 2</summary>

Scenario A: de gebruiker is geblokkeerd. De server weigert de actie op basis van de autorisatiestatus van de gebruiker. Scenario D: het product is uitverkocht. De server weigert de actie omdat de gewenste staat conflicteert met de huidige staat. Dit zijn allebei 4xx-fouten, maar om verschillende redenen.

</details>

<details>
<summary>Hint 3</summary>

`403 Forbidden` zegt: "Jij mag dit niet doen." `409 Conflict` zegt: "De actie botst met de huidige staat van de resource." Welk van deze twee past bij een geblokkeerd account, en welk bij een uitverkocht product?

</details>

---

## Part 2: Implementeer een BookingExceptionMapper

### What you will do

Open de startbestanden in de map `starter/`. Je vindt daar:

- `BookingException.java` – de custom exception die het team al heeft aangemaakt
- `BookingExceptionMapper.java` – een skeleton met `TODO`-comments
- `ErrorBody.java` – de klasse voor de error response body

Jouw taak: implementeer `BookingExceptionMapper` zodat:

- De klasse als Jakarta REST provider geregistreerd wordt
- De juiste interface geïmplementeerd wordt
- De response `409 Conflict` teruggeeft
- De body een `ErrorBody` bevat met de `errorCode` uit de exception, de message en de huidige timestamp
- De `Content-Type` `application/json` is

### Success criteria

- `@Provider` staat op de klasse
- De klasse implementeert `ExceptionMapper<BookingException>`
- `toResponse()` retourneert een `Response` met statuscode `409`
- De body bevat een `ErrorBody` met `code`, `message` en `timestamp`
- Er lekt geen stacktrace of interne informatie naar de response

### Hints

<details>
<summary>Hint 1</summary>

De interface die je moet implementeren is `ExceptionMapper<BookingException>` uit het pakket `jakarta.ws.rs.ext`. De `@Provider`-annotatie komt ook uit `jakarta.ws.rs.ext`.

</details>

<details>
<summary>Hint 2</summary>

De enige methode die je moet implementeren is `toResponse(BookingException exception)`. Die geeft een `Response` terug. Je bouwt die met de `Response`-builder: status → entity → build.

</details>

<details>
<summary>Hint 3</summary>

Voor de timestamp gebruik je `java.time.Instant.now().toString()`. Voor de statuscode: `Response.Status.CONFLICT` geeft je de 409-constante.

</details>

<details>
<summary>Hint 4</summary>

Structuur van de implementatie:

```java
@Provider
public class BookingExceptionMapper implements ExceptionMapper<BookingException> {

    @Override
    public Response toResponse(BookingException exception) {
        ErrorBody body = new ErrorBody(
            exception.getErrorCode(),
            exception.getMessage(),
            Instant.now().toString()
        );
        return Response
            .status(Response.Status.CONFLICT)
            .entity(body)
            .type(MediaType.APPLICATION_JSON)
            .build();
    }
}
```

</details>

---

## Part 3: Voeg een catch-all mapper toe

### What you will do

Voeg een tweede `ExceptionMapper` toe die _alle_ onverwachte uitzonderingen (`Exception`) afvangt. Deze mapper moet:

1. De stacktrace **intern loggen** (gebruik `Logger` of `System.err.println`)
2. Een `500 Internal Server Error` terugsturen
3. De body bevat alleen een generieke `ErrorBody` met code `INTERNAL_ERROR` en een niet-specifieke message – **geen** stacktrace, klassenamen of details

Maak hiervoor een nieuw bestand `GenericExceptionMapper.java` in de `starter/`-map.

### Success criteria

- De klasse is geregistreerd als `@Provider`
- Ze implementeert `ExceptionMapper<Exception>`
- De stacktrace wordt intern gelogd
- De response body bevat geen stacktrace, klassenamen of andere interne informatie
- De statuscode is `500`

### Hints

<details>
<summary>Hint 1</summary>

Een `Logger` aanmaken: `private static final Logger LOGGER = Logger.getLogger(GenericExceptionMapper.class.getName());`
Om de stacktrace te loggen: `LOGGER.log(Level.SEVERE, "Unhandled exception", exception);`

</details>

<details>
<summary>Hint 2</summary>

De message in de error body mag generiek zijn, zoals `"An unexpected error occurred. Please try again later."`. Zet er geen informatie in over _wat_ er mis ging – dat is voor de logs, niet voor de client.

</details>

---

# Bonus Challenge

In productiesystemen wil je fouten kunnen correleren: als een client een fout meldt, moet je hem kunnen terugvinden in de logs. Voeg een **correlatie-ID** toe aan je foutafhandeling:

1. Genereer in de `GenericExceptionMapper` een uniek ID met `UUID.randomUUID().toString()`
2. Log de stacktrace samen met dit ID: `"[correlationId=" + id + "] Unhandled exception"`
3. Voeg het correlatie-ID toe aan de error body zodat de client het kan rapporteren
4. Breid `ErrorBody` uit met een `correlationId`-veld

---

# Reflection Questions

1. Waarom is het een beveiligingsrisico om een stacktrace terug te sturen naar de client? Noem twee concrete soorten informatie die een stacktrace kan bevatten en leg uit hoe een aanvaller die zou kunnen misbruiken.

2. Stel dat je geen `ExceptionMapper` registreert voor `BookingException`. Wat geeft Jersey/Jakarta REST dan standaard terug als die exception gegooid wordt? Wat zijn de gevolgen voor de client?

3. Een collega stelt voor om in de `GenericExceptionMapper` de `exception.getMessage()` wél in de response body op te nemen, "want dan weet de client tenminste wat er mis ging." Wat zijn de bezwaren hiertegen?

4. Wanneer zou je kiezen voor `409 Conflict` boven `422 Unprocessable Entity`? Geef een concreet voorbeeld van elk.
