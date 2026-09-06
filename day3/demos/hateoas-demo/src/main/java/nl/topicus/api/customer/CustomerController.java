package nl.topicus.api.customer;

import nl.topicus.api.order.OrderController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Every response includes a `_links` block: a `self` link plus links to
 * related resources, so a client can navigate the API instead of hardcoding URLs.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public EntityModel<Customer> getCustomer(@PathVariable long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer " + id + " was not found"));

        return EntityModel.of(customer,
                linkTo(methodOn(CustomerController.class).getCustomer(id)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getOrders(id)).withRel("orders"));
    }

    @GetMapping
    public CollectionModel<EntityModel<Customer>> getAllCustomers() {
        List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class).getCustomer(customer.id())).withSelfRel(),
                        linkTo(methodOn(OrderController.class).getOrders(customer.id())).withRel("orders")))
                .toList();

        return CollectionModel.of(customers,
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
    }
}
