package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        List<User> users = List.of(new User(), new User());

        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        User user = new User();
        user.setUsername("testUser");

        when(userService.createUser(user)).thenReturn(user);

        User result = userController.createUser(user);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void deleteUser_shouldInvokeServiceDeleteMethod() {
        userController.deleteUser(1L);

        verify(userService, times(1)).deleteUser(1L);
    }
}
