# Lab: Versioning, Backward Compatibility & Contract Review

## Scenario

Het team heeft een eerste versie van de API live. Het werkt voor de huidige gebruikers, maar de product owner wil al beginnen met de nieuwe versie van het contract. Er is een discussie: mag je meteen velden verwijderen, is een nieuwe URL nodig, en wat gebeurt er met bestaande clients? Jullie krijgen de opdracht om het ontwerp te reviewen op compatibiliteit en versioning.

---

## Learning Goals

- Verschillende versioning strategies herkennen en vergelijken
- Backward compatibility beoordelen op een API-contract
- Contract afbeelding en semantische risico's herkennen
- Een bewuste keuze maken tussen API evolution en breaking change

---

## Prerequisites

- Theorie over versioning, compatibility, resource modelling en naming behandeld
- Basiskennis van JSON, endpoints en headers

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Kies een versioning strategie

### What you will do

Je krijgt vier scenario's. Kies voor elk scenario de beste aanpak:

- URL versioning (`/v1/...`)
- Header versioning (`Accept: application/vnd.company.v1+json`)
- Media type versioning
- Geen versioning, maar een expliciete compatibele evolution

| Scenario                                                                     | Beste keuze | Waarom? |
| ---------------------------------------------------------------------------- | ----------- | ------- |
| Een publieke API met honderden externe clients                               |             |         |
| Een interne API met 2 teams die samen werken                                 |             |         |
| Een API waarbij een field wordt toegevoegd en geen client breakt             |             |         |
| Een API waarbij een veld wordt verwijderd en een grote impact verwacht wordt |             |         |

### Success criteria

- Je kunt per scenario een reden geven voor de keuze
- Je weet welke strategie zichtbaar is voor klanten en welke meer “clean” is
- Je begrijpt waarom versioning niet altijd nodig is als je evolutie backward compatible is

### Hints

<details>
<summary>Hint 1</summary>

URL versioning is zichtbaar en eenvoudig, maar vaak minder elegant in de resource URL.

</details>

<details>
<summary>Hint 2</summary>

Als een verandering niet breaking is, kun je vaak zonder versioning uitkomen. Versioning is vooral relevant bij echte contractbreuken.

</details>

---

## Part 2: Bepaal of een wijziging breaking is

### What you will do

Bekijk de volgende wijzigingen en geef aan of dit een breaking change is.

| Wijziging                                                        | Breaking? | Waarom? |
| ---------------------------------------------------------------- | --------- | ------- |
| Een nieuw veld toevoegen aan de response                         |           |         |
| Een bestaand veld verwijderen uit de response                    |           |         |
| Een veld type wijzigen van `string` naar `integer`               |           |         |
| De `status`-veld naam wijzigen van `status` naar `state`         |           |         |
| Een endpoint van `GET /users` naar `GET /customers` verhuizen    |           |         |
| Een `null`-veld laten verdwijnen in plaats van `null` teruggeven |           |         |

### Success criteria

- Je kunt een wijziging classificeren als breaking of niet-breaking
- Je weet waarom typewijzigingen en veldnamen eigenlijk altijd contractbreuken zijn
- Je kunt een veilige migration strategy benoemen zonder direct te versleutelen

---

# Bonus Challenge

Je hebt een API met deze response:

```json
{
  "id": 10,
  "name": "Alice",
  "email": "alice@example.com"
}
```

Je krijgt de vraag om `email` te verwijderen, omdat het te gevoelig is om te tonen. Wat zou je doen?

- Verwijder je direct het veld?
- Laat je het veld nog 1 release lang staan?
- Maak je een nieuwe versie?
- Doe je een `deprecated`-veld of een `maskedEmail`-veld?

Leg je beslissingsproces uit.

---

# Reflection Questions

1. Welke vorm van versioning herken je het snelst in een publieke API en waarom?
2. Hoe ga je om met een contract dat al in gebruik is door een frontend-app?
3. Waarom is het toevoegen van een veld meestal niet-breaking, maar het verwijderen van een veld bijna altijd wel?
4. Wanneer is het beter om een resource te hernoemen dan een nieuwe URL te introduceren?
