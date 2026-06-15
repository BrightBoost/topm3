# Lab: De juiste status code kiezen

## Scenario

Je reviewt een pull request van een stagiaire. Ze heeft een nieuwe REST API gebouwd voor een boeking-systeem, maar de status codes kloppen niet overal. Jij bent de reviewer en jouw taak is om voor elk scenario te bepalen wat de *correcte* status code is en uit te leggen waarom.

---

## Learning Goals

- De juiste HTTP status code kiezen voor veelvoorkomende REST API-scenario's
- Het onderscheid uitleggen tussen 401 en 403, en tussen 200 en 201
- Redeneren over welk type fout een status code signaleert: client of server
- Beoordelen of een gegeven status code in een bepaalde context correct is

---

## Prerequisites

- De theorie over HTTP status codes is behandeld (2xx, 4xx, 5xx)
- Je kent het verschil tussen een client-side en server-side fout

---

# Lab Parts

Dit lab bevat **3 delen**.

---

## Part 1: Koppel scenario aan status code

### What you will do

Hieronder staan acht scenario's. Schrijf voor elk scenario de juiste HTTP status code op en leg in één zin uit waarom dat de juiste keuze is.

| # | Scenario | Status code | Waarom? |
|---|----------|-------------|---------|
| 1 | Een gebruiker vraagt `GET /users/42` op. De gebruiker bestaat en de data wordt correct teruggegeven. | | |
| 2 | Een gebruiker maakt een nieuwe boeking aan via `POST /bookings`. De boeking is aangemaakt en opgeslagen. | | |
| 3 | Een gebruiker vraagt `GET /users/999` op. Er bestaat geen gebruiker met dat ID. | | |
| 4 | Een gebruiker stuurt een `POST /bookings` met een lege body. Het veld `roomId` is verplicht maar ontbreekt. | | |
| 5 | Een gebruiker stuurt een request zonder `Authorization`-header naar een beveiligd endpoint. | | |
| 6 | Een gebruiker is ingelogd maar heeft geen rechten om admin-data op te vragen. | | |
| 7 | Een `DELETE /bookings/10` wordt uitgevoerd. De boeking is verwijderd. Er is niets terug te sturen. | | |
| 8 | De server gooit een `NullPointerException` bij het verwerken van een geldig request. | | |

### Success criteria

- Alle acht status codes zijn correct
- Je kunt voor elk scenario uitleggen *waarom* het die code is

### Hints

<details>
<summary>Hint 1</summary>

Gebruik de stelregel: 2xx = succes, 4xx = fout aan jouw kant (client), 5xx = fout aan de serverkant. Begin daarmee voordat je de exacte code kiest.

</details>

<details>
<summary>Hint 2</summary>

Scenario 2 en 7 zijn valkuilen. Bij aanmaken wil je niet `200 OK` maar een specifiekere success code. Bij verwijderen wil je ook niet altijd een body terugsturen.

</details>

<details>
<summary>Hint 3</summary>

Scenario 5 en 6 lijken op elkaar maar zijn fundamenteel anders. Vraag jezelf af: weet de server *wie* je bent? En: weet de server wie je bent maar mag je dit toch niet?

</details>

---

## Part 2: Beoordeel andermans code

### What you will do

Hieronder staan vier stukjes pseudocode van je nieuwe collega stagiair. Identificeer wat er mis is en schrijf de correcte versie op.

**Fragment A**
```
POST /users  →  body: { name: "Linda", email: "linda@example.com" }
Response: 200 OK
Body: { id: 7, name: "Linda", email: "linda@example.com" }
```

**Fragment B**
```
GET /products/999
Response: 200 OK
Body: {}
```

**Fragment C**
```
DELETE /orders/5
Response: 200 OK
Body: { message: "deleted" }
```

**Fragment D**
```
POST /login  →  body: { username: "barry", password: "wrong" }
Response: 404 Not Found
```

### Success criteria

- Je hebt voor elk fragment de fout benoemd
- Je hebt de correcte status code gegeven
- Je kunt in één zin uitleggen waarom de originele code misleidend of fout is

### Hints

<details>
<summary>Hint 1</summary>

Fragment B is het gevaarlijkste. Een lege body met 200 zegt: "succes, maar er is niets". Klopt dat hier?

</details>

<details>
<summary>Hint 2</summary>

Fragment D: de gebruiker bestaat wel, maar het wachtwoord klopt niet. Is dat een "not found" of iets anders? En hoe zit het met security?

</details>

<details>
<summary>Hint 3</summary>

Fragment C: technisch werkt 200 hier, maar er is een status code die specifieker zegt "gelukt, en er is niets terug te sturen". Welke is dat?

</details>

---

## Part 3: Nabespreking

### What you will do

Bespreek je antwoorden met je buurman/buurvrouw. Zijn jullie het eens? Waar zat verschil? Noteer eventuele status codes waarbij je twijfelde en leg uit wat de twijfel was.

### Success criteria

- Je kunt het onderscheid tussen 401 en 403 uitleggen zonder in je aantekeningen te kijken
- Je kunt het onderscheid tussen 200 en 201 uitleggen met een concreet voorbeeld

### Hints

<details>
<summary>Hint 1</summary>

Als je ergens twijfelt: vraag jezelf af wat de status code communiceert naar de *client*. Wat moet de client doen met dit antwoord? Dat helpt je de juiste keuze maken.

</details>

---

# Bonus Challenge (Optional)

Zoek in de JSONPlaceholder API (`https://jsonplaceholder.typicode.com`) naar een scenario waarbij de API een *onverwachte* status code teruggeeft. Wat had je verwacht? Wat kreeg je? Is het een fout in de API, of was jouw verwachting verkeerd?

