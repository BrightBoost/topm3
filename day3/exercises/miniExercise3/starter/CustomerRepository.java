package nl.topicus.api.customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepository {

    private final Map<Integer, Customer> customers = new HashMap<>();

    public CustomerRepository() {
        customers.put(1, new Customer(1, "Alice"));
        customers.put(2, new Customer(2, "Bob"));
    }

    public Optional<Customer> findById(int id) {
        return Optional.ofNullable(customers.get(id));
    }

    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }
}
