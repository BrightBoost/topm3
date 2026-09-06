package nl.topicus.api.order;

public record Order(long id, long customerId, String description, double amount) {
}
