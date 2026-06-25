package nl.topicus.api.user;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface voor User.
 *
 * In unit tests wordt deze interface gemockt – er is geen echte implementatie nodig.
 * Een echte implementatie zou de database aanspreken via JDBC of een ORM.
 */
public interface UserRepository {

    /**
     * Zoek een gebruiker op ID.
     * Geeft Optional.empty() terug als de gebruiker niet bestaat.
     */
    Optional<User> findById(int id);

    /**
     * Geeft alle gebruikers terug.
     */
    List<User> findAll();

    /**
     * Slaat een nieuwe gebruiker op en geeft de opgeslagen versie terug
     * (inclusief gegenereerd ID).
     */
    User save(User user);

    /**
     * Controleert of er al een gebruiker bestaat met het gegeven e-mailadres.
     */
    boolean existsByEmail(String email);
}
