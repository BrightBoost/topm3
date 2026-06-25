package nl.topicus.api.user;

import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests voor UserService.
 *
 * @ExtendWith(MockitoExtension.class) – laat Mockito de @Mock en @InjectMocks velden initialiseren
 * @Mock                               – maakt een nep-implementatie van UserRepository
 * @InjectMocks                        – maakt een UserService aan en injecteert de mock erin
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // Testdata – herbruikbaar in alle testmethodes
    private final User testUser = new User(1, "Alice", "alice@example.com");

    // -------------------------------------------------------------------------
    // Part 1: Tests voor getUserById
    // -------------------------------------------------------------------------

    @Test
    void getUserById_returnsUser_whenFound() {
        // TODO: implementeer deze test
        //
        // Stap 1 (Arrange): configureer de mock zodat findById(1) de testUser teruggeeft
        //   when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        //
        // Stap 2 (Act): roep userService.getUserById(1) aan
        //
        // Stap 3 (Assert): controleer dat het resultaat de juiste naam heeft
        //   assertEquals("Alice", result.getName());
    }

    @Test
    void getUserById_throwsNotFoundException_whenUserDoesNotExist() {
        // TODO: implementeer deze test
        //
        // Stap 1 (Arrange): configureer de mock zodat findById(99) Optional.empty() teruggeeft
        //   when(userRepository.findById(99)).thenReturn(Optional.empty());
        //
        // Stap 2 (Act + Assert): controleer dat een NotFoundException gegooid wordt
        //   assertThrows(NotFoundException.class, () -> userService.getUserById(99));
    }

    // -------------------------------------------------------------------------
    // Part 2: Tests voor createUser
    // Schrijf deze tests VOORDAT je createUser implementeert in UserService.
    // -------------------------------------------------------------------------

    @Test
    void createUser_savesAndReturnsUser_whenEmailIsNew() {
        // TODO: implementeer deze test
        //
        // Arrange:
        //   - when(userRepository.existsByEmail("alice@example.com")).thenReturn(false);
        //   - when(userRepository.save(any(User.class))).thenReturn(testUser);
        //
        // Act: roep userService.createUser(testUser) aan
        //
        // Assert:
        //   - Controleer dat het resultaat niet null is
        //   - Controleer dat userRepository.save() is aangeroepen:
        //     verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_throwsConflictException_whenEmailAlreadyExists() {
        // TODO: implementeer deze test
        //
        // Arrange:
        //   - when(userRepository.existsByEmail("alice@example.com")).thenReturn(true);
        //
        // Act + Assert:
        //   - assertThrows(ConflictException.class, () -> userService.createUser(testUser));
        //   - verify dat userRepository.save() NIET aangeroepen is:
        //     verify(userRepository, never()).save(any(User.class));
    }
}
