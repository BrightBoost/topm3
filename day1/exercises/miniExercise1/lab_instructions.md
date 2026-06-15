# Lab: Herken de API-stijl

## Scenario

Je staat op het punt bij een nieuw project te beginnen. Het team gebruikt drie verschillende externe systemen, elk met een eigen API. Jouw tech lead vraagt jou om voorafgaand aan de technische kickoff meeting de drie APIs te analyseren: welke stijl gebruiken ze, wat zijn de voor- en nadelen in deze context, en welke stijl zou jij zelf kiezen voor het nieuwe systeem dat het team gaat bouwen?

---

## Learning Goals

- REST, RPC en SOAP herkennen aan de hand van de URL-structuur, HTTP-methode en berichtformaat
- Per stijl de ontwerpfilosofie verwoorden en koppelen aan concrete kenmerken in een gegeven voorbeeld
- De voor- en nadelen van elke stijl afwegen in een specifieke context
- Een beredeneerde keuze maken voor een API-stijl gegeven een set van projecteisen

---

## Prerequisites

- De theorie over REST, RPC en SOAP uit het ochtendprogramma is behandeld
- Basiskennis HTTP (methods, URL, body)

---

# Lab Parts

Dit lab bevat **3 delen**.

---

## Part 1: Herken de stijl

### What you will do

Hieronder staan drie versies van dezelfde functionaliteit: een vergaderzaal boeken. Bestudeer elk van de drie en bepaal welke stijl het is: REST, RPC of SOAP. Noteer per versie de kenmerken waaraan je de stijl herkent.

---

**Versie A**

```
POST /api/createRoomBooking
Content-Type: application/json

{
  "roomId": 3,
  "userId": 12,
  "date": "2026-06-05",
  "from": "09:00",
  "to": "10:00"
}
```

---

**Versie B**

```
POST /bookingService
Content-Type: text/xml

<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <CreateRoomBookingRequest>
      <roomId>3</roomId>
      <userId>12</userId>
      <date>2026-06-05</date>
      <from>09:00</from>
      <to>10:00</to>
    </CreateRoomBookingRequest>
  </soap:Body>
</soap:Envelope>
```

---

**Versie C**

```
POST /rooms/3/bookings
Content-Type: application/json

{
  "userId": 12,
  "date": "2026-06-05",
  "from": "09:00",
  "to": "10:00"
}
```

---

### Success criteria

- Je hebt aan elke versie de correcte stijl (REST, RPC, SOAP) gekoppeld
- Je kunt voor elke versie minimaal twee concrete kenmerken noemen waaraan je de stijl herkent
- Je begrijpt het verschil tussen een resource-georiënteerde URL en een actie-georiënteerde URL

### Hints

<details>
<summary>Hint 1</summary>

Let op de URL. Is het een zelfstandig naamwoord (een ding) of een werkwoord (een actie)? Dat is vaak het eerste signaal.

</details>

<details>
<summary>Hint 2</summary>

Kijk ook naar het berichtformaat in de body. Is het JSON of XML? En is de XML gewoon data, of zit er een speciale omhulling omheen?

</details>

<details>
<summary>Hint 3</summary>

Bij één stijl staat de actie (`createRoomBooking`) in de URL. Bij een andere stijl staat de actie in een XML-element in de body. Bij de derde stijl is de URL de resource (`/rooms/3/bookings`) en drukt de HTTP-methode de actie uit.

</details>

---

## Part 2: Voor- en nadelen

### What you will do

Noteer voor elke stijl een concreet voordeel en een concreet nadeel, specifiek voor de context van een interne boekingsapplicatie bij een middelgroot bedrijf.

Gebruik de volgende tabel als basis:

| Stijl | Voordeel (in deze context) | Nadeel (in deze context) |
| ----- | -------------------------- | ------------------------ |
| REST  |                            |                          |
| RPC   |                            |                          |
| SOAP  |                            |                          |

### Success criteria

- Elk voordeel en nadeel is specifiek voor de boekingscontext, niet alleen een generieke beschrijving
- Je benoemt bij SOAP minstens iets over het contract of de tooling
- Je benoemt bij REST minstens iets over caching, leesbaarheid of standaardisatie
- Je benoemt bij RPC minstens iets over de actie-georiënteerde aanpak of performance

### Hints

<details>
<summary>Hint 1</summary>

Denk na over wie de API gaat gebruiken. Is dat alleen het eigen team, of ook externe partijen? Dat heeft invloed op hoe belangrijk leesbaarheid en standaardisatie zijn.

</details>

<details>
<summary>Hint 2</summary>

Denk aan caching: GET-requests in REST mogen gecached worden door browsers en proxies. Heeft dat voordeel in een intern boekingssysteem? Of is alle data toch dynamisch?

</details>

<details>
<summary>Hint 3</summary>

SOAP verplicht je tot een WSDL-contract. Is dat een last of juist een voordeel als je een strikte integratie met een ander team of bedrijf nodig hebt?

</details>

---

## Part 3: Maak een keuze

### What you will do

Jouw team gaat een nieuw boekingssysteem bouwen. Er zijn twee scenario's. Kies per scenario een API-stijl en onderbouw je keuze in twee à drie zinnen.

**Scenario 1 – Intern systeem**
Het boekingssysteem wordt alleen gebruikt door andere interne services van hetzelfde bedrijf. Performance is belangrijk; beide kanten zijn in handen van het eigen team.

**Scenario 2 – Publieke API**
Het boekingssysteem wordt opengesteld voor externe partijen (vergaderruimtes verhuurders, agenda-apps, etc.). Developers die de API gebruiken, heb je geen controle over.

### Success criteria

- Je maakt per scenario een expliciete keuze
- De onderbouwing koppelt de eigenschap van de gekozen stijl aan een concreet aspect van het scenario
- Je erkent eventuele nadelen van jouw keuze

### Hints

<details>
<summary>Hint 1</summary>

Denk na over wie de API-consumer is en of je die kent. Als jij beide kanten beheert, heb je meer vrijheid. Als de consumer een onbekende externe partij is, telt leesbaarheid en standaardisatie zwaarder.

</details>

<details>
<summary>Hint 2</summary>

"Performance is belangrijk" in een intern systeem kan een argument zijn voor een stijl die weinig overhead heeft en niet afhankelijk is van tekstformaten.

</details>

<details>
<summary>Hint 3</summary>

Er is geen absoluut juist antwoord. Wat telt is dat je jouw keuze kunt onderbouwen met concrete argumenten en de nadelen eerlijk benoemt.

</details>

---

# Bonus Challenge (Optional)

Bedenk een vierde scenario: een groot bedrijf met meerdere legacy-systemen uit de jaren 2000 dat nieuwe services wil integreren. Welke API-stijl verwacht je daar aan te treffen? En als jij een nieuwe service toevoegt aan dit landschap, ga je mee in de bestaande stijl of introduceer je een nieuwe? Onderbouw je keuze.

---