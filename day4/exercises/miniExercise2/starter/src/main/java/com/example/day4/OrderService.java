package com.example.day4;

import java.util.Map;

public class OrderService {
    private final Map<Long, Order> orders = Map.of(
            101L, new Order(101L, 1L, "books"),
            102L, new Order(102L, 2L, "cheese")
    );

    public Order findOrder(User currentUser, long orderId) {
        return orders.get(orderId);
    }

    public void deleteOrder(User currentUser, long orderId) {
        orders.get(orderId);
    }

    public record User(long id, String role) {
    }

    public record Order(long id, long ownerId, String description) {
    }
}
