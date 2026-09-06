# Lab: Integration Tests – Test het HTTP-contract

## Scenario

De service-laag werkt, maar je wilt zeker weten dat de API ook echt werkt zoals de client hem verwacht. Je wilt testen of de juiste endpoints leven, de juiste statuscodes terugkomen, de JSON-body correct is en de foutafhandeling consistent werkt. Deelnemers schrijven een kleine integration test tegen een draaiende app met in-memory persistence.

---

## Learning Goals

- Het verschil tussen unit tests en integration tests begrijpen
- Een simple REST API starten in een testomgeving
- HTTP-responses testen met JUnit en RestAssured
- Happy path en unhappy path testen op het contractniveau
- Geen test schrijven op interne implementatie, maar op de client-zichtbare API

---

## Prerequisites

- Theorie over API testing part 3 behandeld
- Basiskennis Java, JUnit 5, Maven
- Startproject beschikbaar in `starter/`

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Start van een in-memory API en test een happy path

### What you will do

Open het startproject in `starter/`.

Bekijk de classes:

- `Customer.java`
- `CustomerResource.java`
- `CustomerRepository.java`
- `ApplicationConfig.java`

Je gaat een integration test schrijven voor het endpoint:

```http
GET /customers/1
```

**Verwachte resultaten**

- status code `200 OK`
- JSON bevat `id` en `name`
- `Content-Type` is `application/json`

### Success criteria

- Je hebt een test die een echte HTTP-request uitvoert
- De response status is correct
- Je controleert minimaal één JSON veld
- Je gebruikt geen implementation details van de service als assert

### Hints

<details>
<summary>Hint 1</summary>

Gebruik `RestAssured.given()` om een request te bouwen; daarna `when().get("/customers/1")` en `then().statusCode(200)`.

</details>

<details>
<summary>Hint 2</summary>

Controleer JSON met `body("id", equalTo(1))` of `body("name", equalTo("Alice"))`.

</details>

---

## Part 2: Test het unhappy path

### What you will do

Schrijf een tweede test voor:

```http
GET /customers/999
```

**Verwachte resultaten**

- status code `404 Not Found`
- response body bevat een juiste foutmelding

### Success criteria

- De test verwacht `404`
- Je controleert dat een foutbody terugkomt zoals het API-contract voorschrijft
- Je kunt uitleggen waarom unit tests hiervoor niet genoeg zijn

### Hints

<details>
<summary>Hint 1</summary>

Gebruik `statusCode(404)` en controleer de JSON body met `body("message", containsString("not found"))`.

</details>

<details>
<summary>Hint 2</summary>

Het doel is niet om een repository te testen, maar om te verifiëren dat het contract van de API klopt.

</details>

---

# Bonus Challenge

Schrijf een derde test voor het aanmaken van een klant:

```http
POST /customers
```

**Verwachte resultaten**

- status code `201 Created`
- response bevat een `Location`-header
- JSON body bevat de nieuwe `id` en `name`

---

# Reflection Questions

1. Welke bugs zouden unit tests mislopen die een integration test wel ziet?
2. Waarom is `201 Created` belangrijk bij een POST-request?
3. Waarom is een in-memory database handig voor deze tests?
4. Hoe zou je een contract test anders noemen dan een integration test?
