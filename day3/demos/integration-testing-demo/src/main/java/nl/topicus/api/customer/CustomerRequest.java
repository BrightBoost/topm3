package nl.topicus.api.customer;

/**
 * Request body for POST /customers. Deliberately has no id: the server assigns that.
 */
public class CustomerRequest {

    private String name;
    private String email;

    public CustomerRequest() {
        // required by Jackson
    }

    public CustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
