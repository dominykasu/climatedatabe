package lt.ca.javau10.climatedata;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.repositories.UserPreferencesRepository;
import lt.ca.javau10.climatedata.services.UserPreferencesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserPreferencesServiceTests {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @InjectMocks
    private UserPreferencesService userPreferencesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserPreferencesTest() {
        // Arrange
        User user = new User("username", "email", "password");
        User_Preferences pref1 = new User_Preferences(user, "region1", "metric1", "1h");
        User_Preferences pref2 = new User_Preferences(user, "region2", "metric2", "24h");

        when(userPreferencesRepository.findAll()).thenReturn(Arrays.asList(pref1, pref2));

        // Act
        List<User_Preferences> preferences = userPreferencesService.getAllUserPreferences();

        // Assert
        assertEquals(2, preferences.size());
        verify(userPreferencesRepository, times(1)).findAll();
    }

    @Test
    void createUserPreferenceTest() {
        // Arrange
        User user = new User("username", "email", "password");
        User_Preferences preference = new User_Preferences(user,"region", "metric", "1h");

        when(userPreferencesRepository.save(any(User_Preferences.class))).thenReturn(preference);

        // Act
        User_Preferences createdPreference = userPreferencesService.createUserPreference(1L,"region", "metric", "1h");

        // Assert
        assertNotNull(createdPreference);
        assertEquals("region", createdPreference.getPreferredRegion());
        verify(userPreferencesRepository, times(1)).save(preference);
    }
    @Test
    void getUserPreferenceByIdTest() {
        // Arrange
        User user = new User("username", "email", "password");
        User_Preferences preference = new User_Preferences(user, "region", "metric", "1h");

        when(userPreferencesRepository.findById(1L)).thenReturn(Optional.of(preference));

        // Act
        User_Preferences foundPreference = userPreferencesService.getUserPreferenceById(1L);

        // Assert
        assertNotNull(foundPreference);
        assertEquals("region", foundPreference.getPreferredRegion());
        verify(userPreferencesRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUserPreferenceTest() {
        // Arrange
        User user = new User("username", "email", "password");
        User_Preferences preference = new User_Preferences(user, "region", "metric", "1h");

        when(userPreferencesRepository.findById(1L)).thenReturn(Optional.of(preference));
        doNothing().when(userPreferencesRepository).delete(preference);

        // Act
        userPreferencesService.deleteUserPreference(1L);

        // Assert
        verify(userPreferencesRepository, times(1)).deleteById(1L);
    }
}