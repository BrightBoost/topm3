# API Starter Project

Dit is het startproject voor het middagproject van dag 1.

## Starten

```bash
mvn exec:java
```

Of start de `Main` class vanuit je IDE.

De server start op `http://localhost:8080/api/`.

## Endpoints (voorbeeld)

| Method | URL                  | Beschrijving        |
| ------ | -------------------- | ------------------- |
| GET    | `/api/examples`      | Alle voorbeelden    |
| GET    | `/api/examples/{id}` | Één voorbeeld op ID |

Test ze in Postman en probeer ook een ID op te vragen dat niet bestaat.

## Jouw eigen resource toevoegen

1. Maak een nieuw package aan naast `example`, vernoemd naar jouw domein (bijv. `pokemon`, `cheese`, `movies`)
2. Maak een record voor je resource (bijv. `Pokemon.java`)
3. Maak een resource class aan met `@Path`, `@GET` en `@Produces` (bijv. `PokemonResource.java`)
4. Verwijder `ExampleResource.java` en `Example.java` als je klaar bent

## Projectstructuur

```
src/main/java/nl/topicus/api/
├── Main.java                    ← start de server
└── example/
    ├── Example.java             ← voorbeeld record (verwijder later)
    └── ExampleResource.java     ← voorbeeld resource (verwijder later)
```
