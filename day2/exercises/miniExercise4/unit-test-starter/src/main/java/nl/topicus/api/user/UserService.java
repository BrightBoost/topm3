package nl.topicus.api.user;

import jakarta.ws.rs.NotFoundException;

import java.util.List;

/**
 * Service laag voor User.
 *
 * Bevat de businesslogica los van HTTP en de database.
 * Dit is de klasse die je gaat unit-testen.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Geeft een gebruiker terug op ID.
     * Gooit een NotFoundException als de gebruiker niet bestaat.
     */
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * Geeft alle gebruikers terug.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Maakt een nieuwe gebruiker aan.
     *
     * TODO (Part 2): implementeer deze methode.
     *
     * Businesslogica:
     *   - Als userRepository.existsByEmail(user.getEmail()) true teruggeeft,
     *     gooi dan een ConflictException met een duidelijke boodschap
     *   - Anders: sla de gebruiker op via userRepository.save(user) en geef het resultaat terug
     */
    public User createUser(User user) {
        // TODO: implementeer deze methode
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
