# Lab: Handmatig testen met Postman

## Scenario

Voordat je je eigen REST API bouwt, wil je eerst begrijpen hoe een bestaande REST API werkt vanuit het perspectief van een client. Je gaat JSONPlaceholder verkennen. Dit is een publieke nep-API die echte REST-responses teruggeeft zonder dat er echt data wordt opgeslagen. 

---

## Learning Goals

- Een Postman-omgeving opzetten met een environment variable voor de base URL
- Een HTTP request opbouwen met de juiste method, URL, headers en body
- De onderdelen van een HTTP response interpreteren: status code, headers en body
- Foutscenario's reproduceren en de HTTP status codes begrijpen die daarbij horen
- Requests opslaan in een Postman collection voor hergebruik

---

## Prerequisites

- Postman geïnstalleerd (download via [postman.com/downloads](https://www.postman.com/downloads/))
- De theorie over HTTP requests, responses en status codes is behandeld

---

# Lab Parts

Dit lab bevat **5 delen**.

---

## Part 1: Postman opzetten

### What you will do

Maak een Postman environment aan met een variabele `base_url` die verwijst naar `https://jsonplaceholder.typicode.com`. Maak daarna een nieuwe collection aan genaamd "JSONPlaceholder". Alle requests uit dit lab sla je op in deze collection.

### Success criteria

- Er is een environment aangemaakt met de variabele `base_url`
- De environment is actief geselecteerd in Postman
- Er is een collection aangemaakt genaamd "JSONPlaceholder"

### Hints

<details>
<summary>Hint 1</summary>

Environments vind je in Postman via het tandwieltje rechtsboven, of via de "Environments" tab in de zijbalk.

</details>

<details>
<summary>Hint 2</summary>

Maak een nieuwe environment aan en voeg een variabele toe met de naam `base_url` en als value `https://jsonplaceholder.typicode.com`. Let op: geen trailing slash aan het einde.

</details>

<details>
<summary>Hint 3</summary>

In een request gebruik je de variabele als: `{{base_url}}/posts`. Postman vervangt `{{base_url}}` automatisch met de waarde uit de actieve environment.

</details>

---

## Part 2: Je eerste GET-requests

### What you will do

Maak twee GET-requests:

1. Haal alle posts op: `GET {{base_url}}/posts`
2. Haal een specifieke post op: `GET {{base_url}}/posts/1`

Inspecteer de response: let op de status code, de `Content-Type` header en de structuur van de JSON body. Sla beide requests op in je collection.

### Success criteria

- Beide requests geven een `200 OK` terug
- Je kunt de status code, de `Content-Type` header en de body aanwijzen in de Postman interface
- Je kunt beschrijven wat het verschil is in de response body van de twee requests
- Beide requests zijn opgeslagen in de collection

### Hints

<details>
<summary>Hint 1</summary>

Selecteer `GET` als method in Postman, voer de URL in en klik op Send. De response verschijnt onderaan het scherm.

</details>

<details>
<summary>Hint 2</summary>

Klik op de "Headers" tab in het response-gedeelte om alle response headers te zien, inclusief `Content-Type`.

</details>

<details>
<summary>Hint 3</summary>

De eerste request geeft een JSON array terug; de tweede geeft een JSON object. Dat is het kernverschil tussen "geef me alles" en "geef me één specifiek item".

</details>

---

## Part 3: Filteren met query parameters

### What you will do

Haal alle posts op van de gebruiker met `userId` 1, met behulp van een query parameter: `GET {{base_url}}/posts?userId=1`. Vergelijk het aantal resultaten met de response op `GET {{base_url}}/posts`. Sla het request op in je collection.

### Success criteria

- De response bevat alleen posts met `"userId": 1`
- Je kunt het verschil in aantal resultaten verklaren
- Het request is opgeslagen in je collection

### Hints

<details>
<summary>Hint 1</summary>

Query parameters beginnen na het `?` teken in de URL en hebben het formaat `naam=waarde`. Je kunt ze ook invoeren via de "Params" tab in Postman, dan vult Postman de URL automatisch aan.

</details>

<details>
<summary>Hint 2</summary>

Gebruik de Params tab: voeg een key `userId` toe met value `1`. Postman voegt dat automatisch als `?userId=1` toe aan de URL.

</details>

---

## Part 4: Een POST-request maken

### What you will do

Maak een nieuw bericht aan via `POST {{base_url}}/posts`. Stuur een JSON body met de velden `title`, `body` en `userId`. Let goed op de `Content-Type` header die je moet meesturen, en inspecteer de status code en response body. Sla het request op.

### Success criteria

- De request geeft een `201 Created` status code terug
- De response body bevat de meegestuurde velden plus een gegenereerd `id`
- Je hebt de `Content-Type: application/json` header meegestuurd
- Het request is opgeslagen in je collection

### Hints

<details>
<summary>Hint 1</summary>

Verander de method naar POST. Bij POST heb je een request body nodig: ga naar de "Body" tab, kies "raw" en selecteer "JSON" in het dropdown-menu rechts.

</details>

<details>
<summary>Hint 2</summary>

Een minimale request body ziet er zo uit:

```json
{
  "title": "Mijn eerste post",
  "body": "Dit is de inhoud",
  "userId": 1
}
```

</details>

<details>
<summary>Hint 3</summary>

Als je de body instelt op "raw JSON" in Postman, voegt Postman automatisch de `Content-Type: application/json` header toe. Controleer dit via de "Headers" tab van je request.

</details>

<details>
<summary>Hint 4</summary>

JSONPlaceholder doet alsof hij de post opslaat maar bewaart niets echt. Het `id` in de response is altijd `101`. Dat is normaal voor deze nep-API — in je eigen API zul je een echte database gebruiken.

</details>

---

## Part 5: Foutscenario's verkennen

### What you will do

Probeer een post op te halen die niet bestaat: `GET {{base_url}}/posts/99999`. Inspecteer de status code en de response body. Vergelijk dit met de succesvolle response van Part 2. Sla ook dit request op.

### Success criteria

- Je ontvangt een `404 Not Found` status code
- Je kunt het verschil beschrijven tussen een `200` en een `404` response, zowel in status code als in body
- Je begrijpt waarom een API een `404` teruggeeft in plaats van een lege response

### Hints

<details>
<summary>Hint 1</summary>

Een HTTP 404 betekent: de resource bestaat niet op dit adres. Het is een fundamenteel ander signaal dan een lege lijst — een lege lijst (`[]`) betekent "er zijn geen items", terwijl een 404 betekent "deze URL bestaat niet".

</details>

<details>
<summary>Hint 2</summary>

Vergelijk de response body van de 404 met die van de 200. Wat staat er in de body bij een 404? Is dat nuttig voor een client die de fout wil afhandelen?

</details>

<details>
<summary>Hint 3</summary>

Noteer je observatie: "Een goede API geeft een 404 terug als een resource niet bestaat, en een lege array als de collectie bestaat maar leeg is." Dit is een principe dat je in je eigen API gaat toepassen.

</details>

---

# Bonus Challenge (Optional)

Gebruik `curl` op de command line om één van de requests uit dit lab te herhalen. Open een terminal en voer de volgende commando's uit:

```bash
# GET request
curl https://jsonplaceholder.typicode.com/posts/1

# POST request
curl -X POST https://jsonplaceholder.typicode.com/posts \
  -H "Content-Type: application/json" \
  -d '{"title": "curl post", "body": "via command line", "userId": 1}'
```

Vergelijk de output met wat Postman laat zien. Wat is een voordeel van curl ten opzichte van Postman? En een nadeel?


