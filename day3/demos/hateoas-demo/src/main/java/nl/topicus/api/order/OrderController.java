package nl.topicus.api.order;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/customers/{customerId}/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public CollectionModel<EntityModel<Order>> getOrders(@PathVariable long customerId) {
        List<EntityModel<Order>> orders = orderRepository.findByCustomerId(customerId).stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrders(customerId)).withRel("orders")))
                .toList();

        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getOrders(customerId)).withSelfRel());
    }
}
