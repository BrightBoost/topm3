package nl.topicus.api.customer;

import java.util.List;

public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer " + id + " was not found"));
    }

    public Customer create(CustomerRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new InvalidCustomerException("Field 'name' is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new InvalidCustomerException("Field 'email' is required");
        }
        return repository.save(new Customer(0, request.getName(), request.getEmail()));
    }
}
