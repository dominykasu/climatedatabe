package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.services.UserPreferencesService;
import lt.ca.javau10.climatedata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserPreferencesControllerTest {

    @Mock
    private UserPreferencesService userPreferencesService;

    @Mock
    private UserService userService;

    @Mock
    private Principal principal;

    @InjectMocks
    private UserPreferencesController userPreferencesController;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a test user
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
    }

    @Test
    void getAllPreferences_shouldReturnUserPreferences_whenUserExists() {
        // Mock Principal to return a valid username
        when(principal.getName()).thenReturn("testUser");

        // Mock UserService to return a valid User
        when(userService.getUserByUsername("testUser")).thenReturn(testUser);

        // Mock UserPreferencesService to return preferences for the user
        List<User_Preferences> preferences = List.of(new User_Preferences());
        when(userPreferencesService.getUserPreferencesByUserId(1L)).thenReturn(preferences);

        // Call the method and assert the results
        ResponseEntity<List<User_Preferences>> response = userPreferencesController.getAllPreferences(principal);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllPreferences_shouldReturn404_whenUserDoesNotExist() {
        // Mock Principal to return a username
        when(principal.getName()).thenReturn("nonexistentUser");

        // Mock UserService to return null (user not found)
        when(userService.getUserByUsername("nonexistentUser")).thenReturn(null);

        // Call the method and verify that an IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            userPreferencesController.getAllPreferences(principal);
        });
    }
}
