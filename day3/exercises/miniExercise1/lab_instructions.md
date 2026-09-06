# Lab: API Design Review – Resource Modelling, Naming & Contract Smells

## Scenario

Je werkt in een team dat een API heeft opgezet zonder een expliciet contract. Een aantal endpoints lijken technisch te werken, maar het model is onduidelijk, de URL's zijn inconsistent en het is onduidelijk welke fields door clients echt gebruikt mogen worden. Je krijgt de opdracht om de API te reviewen op resource modelling en contract kwaliteit.

---

## Learning Goals

- REST resources herkennen en van actie-georiënteerde endpoints onderscheiden
- Onjuiste URL-structuren en naming decisions signaleren
- Contract issues herkennen zoals onduidelijke velden, null-versus-missing en breaking changes
- Een API review doen op consistentie, semantiek en compatibiliteit

---

## Prerequisites

- Theorie over REST design principles, resources, collections en versioning behandeld
- Basiskennis van HTTP-methodes en JSON-structuren

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Herken slechte resource design

### What you will do

Je krijgt het volgende set van endpoints. Onderstreep voor elk endpoint of het goed of slecht REST-design is, en geef een betere alternatieve vorm.

| Endpoint                        | Ja/Nee | Waarom? | Beter alternatief |
| ------------------------------- | ------ | ------- | ----------------- |
| `/getAllUsers`                  |        |         |                   |
| `/customers/42/profile/details` |        |         |                   |
| `/orders/delete/99`             |        |         |                   |
| `/users/42/orders`              |        |         |                   |
| `/products?id=12`               |        |         |                   |
| `/v1/customers`                 |        |         |                   |

### Success criteria

- Je kunt voor elk endpoint uitleggen of de URL semantisch logisch is
- Je weet wanneer iets een actie is en dus niet in een resource URL thuishoort
- Je kunt een betere resource-structuur voorstellen zonder de business-opzet te veranderen

### Hints

<details>
<summary>Hint 1</summary>

Een URL die een werkwoord bevat, is vaak een signaal dat je de actie als resource modelleert in plaats van als echte resource.

</details>

<details>
<summary>Hint 2</summary>

Als je een resource hebt met een relatie, is het vaak logisch om een collectie of subresource te gebruiken. Maar te diep nesten kan ook onoverzichtelijk worden.

</details>

---

## Part 2: Contract smells

### What you will do

Bekijk deze response voorbeelden en markeer welke “contract smells” je ziet.

**Voorbeeld A**

```json
{
  "status": null,
  "email": "",
  "lastLogin": "12-08-2026"
}
```

**Voorbeeld B**

```json
{
  "error": "User not found",
  "message": "Het record bestaat niet"
}
```

**Voorbeeld C**

```json
{
  "id": 10,
  "name": "Alice",
  "createdAt": "2026-08-25T12:00:00"
}
```

**Vragen**

1. Welke semantische problemen zie je in voorbeeld A?
2. Wat is het risico van inconsistent foutformaat in voorbeeld B?
3. Welke keuze is beter voor datum-format in voorbeeld C?
4. Wat zou je veranderen om backward compatibility te verbeteren?

### Success criteria

- Je benoemt minimaal drie contract smells
- Je motiveert waarom `null`, `""`, locale datums en onduidelijke foutvormen problematisch zijn
- Je weet wat een veilige evolution path is zonder een breaking change te introduceren

---

# Bonus Challenge

Je hebt een API met deze response:

```json
{
  "id": 42,
  "name": "Alice",
  "role": "admin"
}
```

Nu wil je `role` verwijderen uit de response zonder bestaande clients te breken. Hoe zou je dat aanpakken?

- Maak een versie van de API?
- Verlaag je het veld uit de response met een opt-in parameter?
- Gebruik je een nieuwe resource variant?

Leg je keuze uit.

---

# Reflection Questions

1. Wanneer is een URL "te diep" genest? Noem een concreet voorbeeld.
2. Wat is het verschil tussen een breaking change en een backward compatible change?
3. Waarom is `null` vaak riskanter dan een ontbrekend veld?
4. Wanneer zou je bewust kiezen voor een `v1`-variant in plaats van een "schoon" model zonder versie?
