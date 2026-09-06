# HATEOAS Demo

Demo for day 3, section 2 ("HATEOAS: Theory vs Practice").

This is a small Spring Boot API using **Spring HATEOAS** to add a `_links`
block to every response, exactly like the example on the slide: a customer
response links to itself and to its orders, without the client needing to
know the URL pattern in advance.

We used Spring here instead of the plain Jersey stack from the rest of the
day because Spring HATEOAS's link builders (`linkTo(methodOn(...))`) make
building correct, refactor-safe links almost free.

## Running

```bash
mvn spring-boot:run
```

## Things to show in class

1. A plain customer response with links:

   ```bash
   curl -s http://localhost:8080/api/customers/42 | jq
   ```

   ```json
   {
     "id": 42,
     "name": "Alice",
     "email": "alice@example.com",
     "_links": {
       "self": { "href": "http://localhost:8080/api/customers/42" },
       "orders": { "href": "http://localhost:8080/api/customers/42/orders" }
     }
   }
   ```

2. Follow the `orders` link without hardcoding it — that's the "hypermedia
   as the engine of application state" idea:

   ```bash
   curl -s http://localhost:8080/api/customers/42/orders | jq
   ```

3. The collection endpoint also links back to itself:

   ```bash
   curl -s http://localhost:8080/api/customers | jq
   ```

4. A missing customer still returns a normal `404` (`ResponseStatusException`)
   — HATEOAS only changes the shape of successful responses, not error
   handling.

   ```bash
   curl -i http://localhost:8080/api/customers/999
   ```

## Main points

See how much of this is generated for you by
`linkTo(methodOn(CustomerController.class).getCustomer(id))`: the link is
built from the actual controller method, so if the route ever changes, the
link changes with it. That safety is the practical selling point; the
philosophical selling point (a client that "discovers" the API) is what most
teams trade away in favor of documentation, as discussed on the slide.
