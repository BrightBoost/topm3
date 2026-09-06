package nl.topicus.api.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory data, seeded to match the slide's `/customers/42` example.
 */
@Repository
public class CustomerRepository {

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();

    public CustomerRepository() {
        customers.put(42L, new Customer(42L, "Alice", "alice@example.com"));
        customers.put(43L, new Customer(43L, "Bob", "bob@example.com"));
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(customers.get(id));
    }

    public List<Customer> findAll() {
        return List.copyOf(customers.values());
    }
}
