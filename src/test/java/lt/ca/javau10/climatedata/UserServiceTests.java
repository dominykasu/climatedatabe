package lt.ca.javau10.climatedata;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.repositories.UserRepository;
import lt.ca.javau10.climatedata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsersTest() {
        // Arrange
        User user1 = new User("username1", "email1", "password1", "role1");
        User user2 = new User("username2", "email2", "password2", "role2");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUserTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User createdUser = userService.createUser(user);

        // Assert
        assertNotNull(createdUser);
        assertEquals("username", createdUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserByIdTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(1L);

        // Assert
        assertNotNull(foundUser);
        assertEquals("username", foundUser.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }
    @Test
    public void updateUserTest() {
        User existingUser = new User();
        existingUser.setUsername("Old Name");
        existingUser.setEmail("oldemail@example.com");

        User updatedUser = new User();
        updatedUser.setUsername("New Name");
        updatedUser.setEmail("newemail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        userService.updateUser(1L, updatedUser);

        verify(userRepository).save(argThat(user ->
                user.getUsername().equals("New Name") &&
                        user.getEmail().equals("newemail@example.com")
        ));
    }

    @Test
    void deleteUserTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}