# Lab: Testing Levels – Contract, Integration & E2E

## Scenario

Je hebt een ER-achtige discussie met je team: "We testen gewoon service logic, toch? Waarom hebben we ook nog integration tests nodig?" Jullie krijgen een pakket van scenario's en moeten bepalen op welk niveau ze het beste getoetst worden.

---

## Learning Goals

- De verschillende testniveaus onderscheiden: unit, integration en end-to-end
- Een test kiezen op basis van wat precies getest moet worden
- Het verschil tussen een contract test en een happy-path test benoemen
- Redeneren over wat een test wel en niet afdekt

---

## Prerequisites

- Theorie over testing pyramid en API testing behandeld
- Basiskennis JUnit 5, API endpoints en HTTP-statuscodes

---

# Lab Parts

Dit lab bevat **2 delen**.

---

## Part 1: Koppel scenario aan testniveau

### What you will do

Deel de volgende scenario's in:

1. unit test
2. integration test
3. end-to-end test

| Scenario                                                                           | Niveau | Waarom? |
| ---------------------------------------------------------------------------------- | ------ | ------- |
| Een repository `findByEmail` geeft een bestaande gebruiker terug                   |        |         |
| `GET /customers/42` geeft `200 OK` en juiste JSON                                  |        |         |
| `POST /customers` zonder verplichte velden geeft `400 Bad Request`                 |        |         |
| Een volledige flow van UI → API → DB werkt                                         |        |         |
| Een service methode `createUser` gooit een `ConflictException` bij duplicate email |        |         |

### Success criteria

- Elk scenario is in het juiste niveau geplaatst
- Je kunt uitleggen waarom een unit test niet hetzelfde is als een contract test
- Je benoemt welk niveau het dichtstbij het echte gebruikersgedrag zit

---

## Part 2: Fix the failing assertion

### What you will do

Je krijgt de volgende test:

```java
@Test
void createUser_returnsUser_whenEmailIsNew() {
    User user = new User("Alice", "alice@example.com");
    when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(user);

    User result = userService.createUser(user);

    assertEquals("Alice", result.getName());
}
```

**Vraag**

- Wat is hier mis aan de test?
- Waar test deze assert eigenlijk alleen?
- Hoe zou je de test verbeteren zodat hij het echte contract of de echte business rule test?

### Success criteria

- Je benoemt dat een test zonder `verify` niet zeker weet of `save` écht is aangeroepen
- Je begrijpt het verschil tussen een assert op input/output en een assert op werkelijke side effect
- Je kunt aangeven hoe je een contract test beter opbouwt

---

# Bonus Challenge

Schrijf een korte teststrategie voor een kleine API met 3 endpoints:

- `GET /customers/{id}`
- `POST /customers`
- `DELETE /customers/{id}`

Benoem:

- welke tests je minimaal wilt hebben in de unitlaag
- welke tests je minimaal wilt hebben in de integrationlaag
- wat je in E2E test en waarom

---

# Reflection Questions

1. Wat ontwikkelt een team als het alleen unit tests schrijft?
2. Wat ontwikkelt een team als het alleen integration tests schrijft?
3. Waarom is een testing pyramid nuttig in API-ontwikkeling?
4. Wanneer is een E2E test echt nodig en wanneer is het te duur?
