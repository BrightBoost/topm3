package nl.topicus.api.customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Talks to a real H2 database over plain JDBC. Used both when running the API
 * for real and, against a different database name, in the integration tests.
 */
public class H2CustomerRepository implements CustomerRepository {

    private final String jdbcUrl;

    public H2CustomerRepository(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT id, name, email FROM customers ORDER BY id";
        List<Customer> customers = new ArrayList<>();
        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                customers.add(map(resultSet));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load customers", e);
        }
    }

    @Override
    public Optional<Customer> findById(long id) {
        String sql = "SELECT id, name, email FROM customers WHERE id = ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(map(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load customer " + id, e);
        }
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                long generatedId = keys.getLong(1);
                return new Customer(generatedId, customer.getName(), customer.getEmail());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save customer", e);
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(jdbcUrl);
    }

    private Customer map(ResultSet resultSet) throws SQLException {
        return new Customer(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("email"));
    }
}
