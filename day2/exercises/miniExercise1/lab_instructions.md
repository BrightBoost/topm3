# Lab: HTTP-Methodes – Scenario's & Annotatie-bugs

## Scenario

Je hebt zojuist een code review ontvangen van een collega. Ze heeft een nieuwe `OrderResource` aangemaakt, maar de HTTP-annotaties kloppen niet op meerdere plekken. Jij bent aangewezen als reviewer en moet de fouten opsporen, uitleggen _waarom_ ze fout zijn en de correcte versie aanleveren. Daarvoor moet je ook weten welke methode hoort bij welke actie – en waarom dat uitmaakt.

---

## Learning Goals

- De juiste HTTP-methode koppelen aan een REST-operatie en de keuze onderbouwen
- Idempotentie herkennen als eigenschap van een HTTP-methode en de praktische consequenties ervan benoemen
- Het verschil tussen `PUT` en `PATCH` toepassen op een concreet geval
- Incorrecte HTTP-annotaties in Jakarta REST-code identificeren en corrigeren
- Redeneren over de gevolgen van een foutieve HTTP-methode voor caching, retries en API-betrouwbaarheid

---

## Prerequisites

- De theorie over HTTP-methodes, idempotentie en PUT vs PATCH is behandeld
- Basiskennis Jakarta REST annotaties: `@GET`, `@POST`, `@PUT`, `@PATCH`, `@DELETE`, `@Path`, `@PathParam`, `@QueryParam`

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Koppel scenario aan HTTP-methode

### What you will do

Hieronder staan zeven scenario's. Schrijf voor elk scenario de correcte HTTP-methode op en leg in één zin uit waarom dat de juiste keuze is. Geef bij relevante scenario's ook aan of de operatie idempotent is.

| #   | Scenario                                                                                          | HTTP-methode | Waarom? | Idempotent? |
| --- | ------------------------------------------------------------------------------------------------- | ------------ | ------- | ----------- |
| 1   | Een gebruiker vraagt zijn profielinformatie op                                                    |              |         |             |
| 2   | Een gebruiker verandert alleen zijn e-mailadres (de rest van het profiel blijft hetzelfde)        |              |         |             |
| 3   | Een beheerder verwijdert een bericht                                                              |              |         |             |
| 4   | Een webshop legt een nieuwe bestelling vast                                                       |              |         |             |
| 5   | Een app synchroniseert een volledig gebruikersprofiel (alle velden) vanuit een ingevuld formulier |              |         |             |
| 6   | Een monitoring tool controleert elke minuut of een service bereikbaar is                          |              |         |             |
| 7   | Een gebruiker "liket" een bericht (de liketeller gaat met 1 omhoog)                               |              |         |             |

Discussievraag voor scenario 7: is een like idempotent? Wat zijn de gevolgen als dat wel of niet zo is?

### Success criteria

- Alle zeven methodes zijn correct ingevuld
- Je kunt voor elk scenario uitleggen waarom het die methode is
- Je hebt voor scenario 7 een beredeneerd standpunt over idempotentie

### Hints

<details>
<summary>Hint 1</summary>

Begin bij de vraag: verandert deze actie iets aan de server, of alleen aan wat je terugkrijgt? Dat onderscheid is het eerste filter.

</details>

<details>
<summary>Hint 2</summary>

Scenario 2 en 5 lijken op elkaar maar zijn fundamenteel anders. Stel jezelf de vraag: stuurt de client alle velden mee, of alleen het veld dat verandert? Dat bepaalt het verschil tussen `PUT` en `PATCH`.

</details>

<details>
<summary>Hint 3</summary>

Voor scenario 7: een like is idempotent als twee keer liken hetzelfde resultaat geeft als één keer liken. Is dat zo? Hangt dat af van hoe je het implementeert?

</details>

---

## Part 2: Annotatie-bugs opsporen en repareren

### What you will do

Open het startbestand `starter/OrderResource.java`. De klasse bevat vier methodes, elk met een fout in de HTTP-annotaties. Jouw taak:

1. Identificeer voor elke methode welke annotatie fout is
2. Leg per methode in één zin uit wat het probleem is en wat de consequentie is (bijv. voor caching, retries of veiligheid)
3. Schrijf de gecorrigeerde versie van de annotaties

Je hoeft de method body niet aan te passen, alleen de annotaties.

### Success criteria

- Alle vier de fouten zijn gevonden
- Je kunt voor elke fout uitleggen wat er concreet mis gaat als je de foutieve annotatie zou gebruiken
- De gecorrigeerde versie compileert en heeft de juiste annotaties

### Hints

<details>
<summary>Hint 1</summary>

Lees elke methode-body als hint: de naam en de logica van de code vertellen je wat de methode _bedoelt_ te doen. Vergelijk dat met de annotatie.

</details>

<details>
<summary>Hint 2</summary>

Fout 1 en 2 gaan over het gebruik van `@GET` voor iets wat de server-staat wijzigt. Vraag jezelf af: wat doet een CDN of een browser met `GET`-requests? En wat is het gevolg als de body van die methode iets aanmaakt of verwijdert?

</details>

<details>
<summary>Hint 3</summary>

Fout 3 gaat over een methode die slechts één veld bijwerkt (het afleveradres), maar `@POST` gebruikt. Fout 4 gaat over een methode die de volledige resource vervangt maar `@PATCH` gebruikt. Wat is het semantische verschil?

</details>

<details>
<summary>Hint 4</summary>

Gecorrigeerde annotaties per methode:

- Methode 1 maakt een resource aan → `@POST`
- Methode 2 verwijdert resources → `@DELETE`
- Methode 3 werkt één veld bij → `@PATCH`
- Methode 4 vervangt de volledige resource → `@PUT`

</details>

---

# Bonus Challenge

Methode 2 verwijdert _alle_ orders van een gebruiker via een `@QueryParam`. In een productiesysteem is dit een gevaarlijke operatie.

Refactor de aanpak:

- Bedenk een REST-conforme URL-structuur voor "verwijder alle orders van gebruiker X" die expliciet aangeeft wat er verwijderd wordt
- Voeg een extra beveiligingslaag toe: zorg dat de aanroeper een bevestiging meestuurt (bijv. een query parameter `confirm=true`) zodat dit niet per ongeluk aangeroepen kan worden
- Schrijf de nieuwe methodesignatuur met de juiste annotaties

---

# Reflection Questions

1. Stel dat een load balancer automatisch mislukte requests opnieuw verstuurt. Welke van de vier gecorrigeerde methodes is het meest riskant als hij dubbel binnenkomt? Licht toe.

2. Een collega stelt voor: "Laten we voor alle endpoints gewoon `POST` gebruiken, dan hoeven we nooit na te denken over idempotentie." Wat zijn de concrete nadelen van dit voorstel?

3. In methode 2 (deleteAllOrdersForUser) is het gebruik van `@GET` met een `?userId=...` query parameter een veelgemaakte fout in de praktijk. Noem twee concrete scenario's buiten code reviews om waarbij dit fout gaat in productie.

4. Wanneer zou je bewust kiezen voor `PUT` boven `PATCH`, ook als je technisch gezien ook `PATCH` zou kunnen gebruiken?
