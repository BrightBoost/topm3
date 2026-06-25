# Lab: Unit Tests – Service Laag Testen met JUnit & Mockito

## Scenario

Het team is tevreden over de API, maar elke keer dat iemand een wijziging doorvoert moet alles handmatig in Postman worden nagelopen. Dat kost een halfuur. Jij krijgt de opdracht om de service laag te voorzien van geautomatiseerde unit tests, zodat toekomstige wijzigingen snel en veilig kunnen worden doorgevoerd. De starter bevat een `UserService` met een aantal methodes – sommige zijn al geïmplementeerd, andere nog niet. Jij schrijft de tests én vult de ontbrekende implementatie in.

---

## Learning Goals

- Unit tests schrijven voor de service laag van een REST API met JUnit 5
- Mocks opzetten met Mockito en het `when(...).thenReturn(...)` patroon toepassen
- Happy path en unhappy path testen voor een gegeven service methode
- Redeneren over wat een unit test wél en niet test en hoe dat afwijkt van een integration test
- Tests een zelfdocumenterende naam geven die het scenario beschrijft

---

## Prerequisites

- De theorie over unit tests, mocks en Mockito is behandeld
- Maven beschikbaar in de terminal (`mvn --version`)
- Het startproject staat in de map `unit-test-starter/`

---

# Lab Parts

Dit lab bevat **3 delen**.

---

## Part 1: Verken het startproject en schrijf je eerste tests

### What you will do

Open het startproject in `unit-test-starter/`. Het bevat:

- `User.java` – een eenvoudig domein-object
- `UserRepository.java` – een interface die de database aanspreekt
- `UserService.java` – de service laag met businesslogica; `getUserById` is al geïmplementeerd
- `UserServiceTest.java` – een testklasse met lege testmethodes die jij moet invullen

Jouw taak voor dit deel:

1. Voer de tests uit: `mvn test` – ze zouden nog moeten falen (of niet compileren)
2. Implementeer de twee testmethodes voor `getUserById`:
   - `getUserById_returnsUser_whenFound` – test de happy path
   - `getUserById_throwsNotFoundException_whenUserDoesNotExist` – test de unhappy path
3. Voer de tests opnieuw uit: beide testen moeten slagen

### Success criteria

- Beide testmethodes zijn geïmplementeerd
- De happy path test configureert de mock zodat `findById` een `Optional.of(user)` teruggeeft
- De happy path test verifieert dat de juiste `User` teruggegeven wordt
- De unhappy path test configureert de mock zodat `findById` een `Optional.empty()` teruggeeft
- De unhappy path test verifieert dat een `NotFoundException` gegooid wordt
- `mvn test` geeft groen

### Hints

<details>
<summary>Hint 1</summary>

Een mock configureren doe je met `when(mockObject.method(argument)).thenReturn(value)`. De mock is al aangemaakt in de testklasse met `@Mock`. Je hoeft hem niet zelf aan te maken.

</details>

<details>
<summary>Hint 2</summary>

Voor de happy path: `when(userRepository.findById(1)).thenReturn(Optional.of(testUser));`
Vervolgens roep je `userService.getUserById(1)` aan en controleer je met `assertEquals` dat het resultaat overeenkomt.

</details>

<details>
<summary>Hint 3</summary>

Voor de unhappy path: stel de mock in zodat hij `Optional.empty()` teruggeeft. Gebruik daarna `assertThrows`:

```java
assertThrows(NotFoundException.class, () -> userService.getUserById(99));
```

</details>

---

## Part 2: Schrijf tests voor createUser

### What you will do

`UserService.createUser` is nog niet geïmplementeerd – de methode heeft een lege body. Jouw taak:

1. Schrijf eerst de tests (test-driven):
   - `createUser_savesAndReturnsUser_whenEmailIsNew` – happy path
   - `createUser_throwsConflictException_whenEmailAlreadyExists` – unhappy path
2. Implementeer daarna `createUser` in `UserService.java` zodat de tests slagen

De verwachte businesslogica:

- Als `userRepository.existsByEmail(email)` `true` teruggeeft, gooi dan een `ConflictException`
- Anders: maak een nieuwe `User` aan, sla hem op via `userRepository.save(user)` en geef hem terug

### Success criteria

- Beide testmethodes zijn aanwezig en beschrijvend benoemd
- De happy path test verifieert dat `userRepository.save()` is aangeroepen (gebruik `verify`)
- De unhappy path test verifieert dat een `ConflictException` gegooid wordt als het e-mailadres al bestaat
- `createUser` is geïmplementeerd in `UserService`
- `mvn test` geeft groen

### Hints

<details>
<summary>Hint 1</summary>

Begin met de unhappy path test – die is eenvoudiger: stel de mock in zodat `existsByEmail` `true` teruggeeft en controleer dat een `ConflictException` gegooid wordt.

</details>

<details>
<summary>Hint 2</summary>

Voor de happy path: stel de mock in zodat `existsByEmail` `false` teruggeeft en `save` de doorgegeven user teruggeeft. Verifieer daarna dat `save` aangeroepen is:

```java
verify(userRepository).save(any(User.class));
```

</details>

<details>
<summary>Hint 3</summary>

Implementatie van `createUser`:

```java
if (userRepository.existsByEmail(user.getEmail())) {
    throw new ConflictException("Email already in use: " + user.getEmail());
}
return userRepository.save(user);
```

</details>

---

## Part 3: Wat testen we NIET met unit tests?

### What you will do

Unit tests op de service laag zijn waardevol, maar ze laten een aantal zaken ongedekt. Dit deel is geen codeer-opdracht, maar een analyseopdracht.

Beantwoord de volgende vragen en bespreek je antwoorden met je buurman/buurvrouw:

1. Stel dat `UserResource` de annotatie `@GET` heeft op de `createUser` methode in plaats van `@POST`. Welke test zou dat aantonen? Welke niet?

2. Stel dat de Jackson JSON-serialisatie het veld `email` weglaat in de response (bijv. door een ontbrekende getter). Welke test zou dat aantonen?

3. Stel dat de service `201 Created` moet teruggeven maar de resource doet `return Response.ok()`. Welke test zou dat aantonen?

4. Wat heb je nodig om deze drie zaken wél te testen?

### Success criteria

- Je kunt voor elk geval uitleggen of een unit test het wel of niet detecteert
- Je kunt benoemen wat voor soort test de ontbrekende dekking wél biedt

---

# Bonus Challenge

Voeg een derde service methode toe: `deleteUser(int id)`.

Schrijf eerst de tests:

- `deleteUser_deletesUser_whenFound` – verify dat `userRepository.deleteById` is aangeroepen
- `deleteUser_throwsNotFoundException_whenUserDoesNotExist` – gooi een `NotFoundException` als de user niet bestaat

Implementeer daarna `deleteUser` zodat alle tests slagen. Voeg ook de nodige methodes toe aan `UserRepository` (als interface) en aan de stub in `UserServiceTest`.

---

# Reflection Questions

1. Je hebt nu tests voor de service laag. Een collega vraagt: "Is het ook zinvol om tests te schrijven voor de repository?" Wat is jouw antwoord, en wat voor soort test zou je dan schrijven?

2. Je hebt `verify(userRepository).save(any(User.class))` gebruikt. Wat zou er mis gaan als je de `verify`-aanroep weglaat uit de happy path test van `createUser`? Zou de test dan nog zinvol zijn?

3. Wat is het verschil tussen `any(User.class)` en het opgeven van een specifieke `User` instantie in `verify`? Wanneer gebruik je welke?

4. Stel dat `UserService.createUser` de `save`-aanroep vergeet (de implementatie retourneert wel een user, maar slaat hem niet op). Welke van jouw tests zou dat detecteren?
