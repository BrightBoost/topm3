# Lab: Statuscodes – Koppelen & Repareren

## Scenario

Je bent net begonnen als developer bij een team dat een product-API beheert. Je collega heeft een nieuwe versie van de `ProductResource` ingecheckt vlak voor ze op vakantie naar Japan ging. De pull request heeft een aardige beschrijving maar de statuscodes kloppen niet. Jij krijgt de taak om de code te reviewen, uit te leggen wat er mis is, en de gecorrigeerde versie te committen voor ze terugkomt.

---

## Learning Goals

- De juiste HTTP-statuscode kiezen voor veelvoorkomende REST-scenario's en de keuze onderbouwen
- Het onderscheid toepassen tussen `200 OK`, `201 Created` en `204 No Content`
- Het onderscheid toepassen tussen `400 Bad Request`, `403 Forbidden`, `404 Not Found` en `500 Internal Server Error`
- Incorrecte `Response`-aanroepen in Jakarta REST-code corrigeren naar de juiste builder-methodes
- Redeneren over de praktische gevolgen van foute statuscodes voor monitoring, caching en API-gebruikers

---

## Prerequisites

- De theorie over HTTP-statuscodes (2xx, 4xx, 5xx) is behandeld
- Basiskennis van de Jakarta REST `Response`-builder API: `Response.ok()`, `Response.status()`, `Response.created()`, `Response.noContent()`

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Koppel situatie aan statuscode

### What you will do

Hieronder staan zeven situaties. Schrijf voor elk de juiste HTTP-statuscode op en leg in één zin uit waarom dat de juiste code is.

| #   | Situatie                                                                         | Statuscode | Waarom? |
| --- | -------------------------------------------------------------------------------- | ---------- | ------- |
| 1   | `GET /users/99` – de gebruiker bestaat niet                                      |            |         |
| 2   | `POST /orders` waarbij het verplichte veld `productId` ontbreekt in de JSON body |            |         |
| 3   | `DELETE /posts/7` – de post is verwijderd, er is niets terug te sturen           |            |         |
| 4   | `POST /users` waarbij de gebruikersnaam al bestaat in de database                |            |         |
| 5   | `GET /admin/reports` – de gebruiker is ingelogd maar heeft geen adminrechten     |            |         |
| 6   | `POST /payments` – de server gooit een onverwachte `NullPointerException`        |            |         |
| 7   | `POST /users` – de nieuwe gebruiker is aangemaakt en opgeslagen                  |            |         |

### Success criteria

- Alle zeven statuscodes zijn correct
- Je kunt per code uitleggen _waarom_ het die specifieke code is en niet een andere in dezelfde categorie

### Hints

<details>
<summary>Hint 1</summary>

Begin bij de categorie: is dit een succes (2xx), een fout van de client (4xx) of een fout van de server (5xx)? Daarna kies je de meest specifieke code binnen die categorie.

</details>

<details>
<summary>Hint 2</summary>

Situatie 3 en 7 zijn allebei succes, maar de code verschilt. Stuur je een body terug? Heb je iets _nieuws_ aangemaakt, of iets _verwijderd_?

</details>

<details>
<summary>Hint 3</summary>

Situatie 4 en 2 zijn allebei client-fouten, maar om verschillende redenen. Bij situatie 2 is de input technisch ongeldig. Bij situatie 4 is de input technisch geldig – maar de staat van de server maakt de actie onmogelijk. Wat zegt dat over de statuscode?

</details>

---

## Part 2: Statuscodes repareren in code

### What you will do

Open het startbestand `starter/ProductResource.java`. De klasse bevat vier endpoints, elk met een foute `return`-statement. Jouw taak:

1. Identificeer voor elk endpoint welke statuscode verkeerd is
2. Leg in één zin uit wat het gevolg is van de foute code (voor een client, voor een monitoring tool, of voor een retry-mechanisme)
3. Schrijf de gecorrigeerde `return`-statement

Gebruik de Jakarta REST `Response`-builder API:

- `Response.ok(entity)` → `200 OK` met body
- `Response.created(uri)` → `201 Created` met `Location`-header
- `Response.noContent()` → `204 No Content`
- `Response.status(Response.Status.NOT_FOUND)` → `404 Not Found`
- `Response.status(Response.Status.BAD_REQUEST).entity(body)` → `400 Bad Request` met body

Je hoeft de overige logica niet aan te passen – alleen de `return`-statements.

### Success criteria

- Alle vier de `return`-statements zijn gecorrigeerd
- Voor de `POST` endpoint bouw je een `URI` en geef je die mee aan `Response.created(uri)`
- Je kunt voor elk endpoint uitleggen waarom de oorspronkelijke code problemen geeft

### Hints

<details>
<summary>Hint 1</summary>

`Response.ok(entity)` geeft altijd `200 OK` terug. Voor een succesvolle aanmaak wil je `201 Created`. Voor een verwijdering zonder body wil je `204 No Content`. Voor een ontbrekende resource wil je `404 Not Found`.

</details>

<details>
<summary>Hint 2</summary>

Voor de `201 Created` response bij `createProduct` heb je een `URI` nodig voor de `Location`-header. Je kunt die bouwen met:

```java
URI location = URI.create("/api/products/" + created.getId());
```

</details>

<details>
<summary>Hint 3</summary>

In de `updateProduct`-methode vangt de `catch`-block alle uitzonderingen op en geeft `500` terug. Maar de code daarboven gooit alleen een `ValidationException`. Dat is een client-fout, geen server-fout. Welke statuscode hoort erbij?

</details>

<details>
<summary>Hint 4</summary>

Gecorrigeerde statementen (zonder de volledige implementatie):

- `createProduct`: `return Response.created(location).entity(created).build();`
- `deleteProduct`: `return Response.noContent().build();`
- `getProduct` (niet gevonden): `return Response.status(Response.Status.NOT_FOUND).build();`
- `updateProduct` (catch): `return Response.status(Response.Status.BAD_REQUEST).entity(errorBody).build();`

</details>

---

# Bonus Challenge

De huidige `createProduct`-methode retourneert de nieuw aangemaakte resource in de body van de `201`-response. Dit is de aanbevolen conventie.

Maar: wat als de aanmaak _asynchroon_ verwerkt wordt? De server accepteert het request, maar de resource is nog niet direct beschikbaar.

- Welke statuscode is dan correct?
- Wat stuur je terug in de response?
- Hoe weet de client wanneer de resource klaar is?

Schrijf een alternatieve versie van `createProduct` die een asynchrone verwerking simuleert (geef gewoon de juiste statuscode terug, de echte async-logica hoef je niet te implementeren).

---

# Reflection Questions

1. Een API geeft altijd `200 OK` terug, ook bij fouten (met een foutbericht in de JSON body). Een collega verdedigt dit: "De client leest toch de body, niet de statuscode." Wat zijn de concrete nadelen van deze aanpak in een productiesysteem met meerdere teams?

2. Je ziet in een logfile dat een endpoint tientallen keren `500 Internal Server Error` teruggeeft. Blijkt het een validatiefout te zijn die niet goed wordt afgehandeld. Wat had je anders moeten doen in de code, en wat is het effect op de client die die foutmeldingen ontvangt?

3. Wat is het verschil in betekenis tussen `404 Not Found` en `410 Gone`? In welk scenario zou je bewust kiezen voor `410`?

4. Je bouwt een delete-endpoint. Een collega stelt voor om `200 OK` met de verwijderde resource als body terug te sturen, in plaats van `204 No Content`. Welke aanpak heeft de voorkeur en waarom? Is er een situatie waarin `200` beter is?
