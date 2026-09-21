package nl.topicus.day4.idor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final Map<Long, Order> orders = Map.of(
            101L, new Order(101L, "alice", "books"),
            102L, new Order(102L, "bob", "cheese"));

    @GetMapping("/vulnerable/orders/{id}")
    public Order vulnerable(@PathVariable long id) {
        return find(id);
    }

    @GetMapping("/secure/orders/{id}")
    public Order secure(@PathVariable long id, Authentication authentication) {
        Order order = find(id);
        if (!order.owner().equals(authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not own this order");
        }
        return order;
    }

    private Order find(long id) {
        Order order = orders.get(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return order;
    }

    private record Order(long id, String owner, String description) {
    }
}
