package nl.topicus.api.order;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {

    private final Map<Long, List<Order>> ordersByCustomerId = new ConcurrentHashMap<>();

    public OrderRepository() {
        ordersByCustomerId.put(42L, List.of(
                new Order(1001L, 42L, "Laptop", 1299.00),
                new Order(1002L, 42L, "Mouse", 25.50)));
        ordersByCustomerId.put(43L, List.of(
                new Order(1003L, 43L, "Keyboard", 75.00)));
    }

    public List<Order> findByCustomerId(long customerId) {
        return ordersByCustomerId.getOrDefault(customerId, List.of());
    }
}
