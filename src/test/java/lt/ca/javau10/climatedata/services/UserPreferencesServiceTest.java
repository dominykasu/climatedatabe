package lt.ca.javau10.climatedata.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.repositories.UserPreferencesRepository;
import lt.ca.javau10.climatedata.services.UserPreferencesService;
import lt.ca.javau10.climatedata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.TransactionSystemException;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

class UserPreferencesServiceTest {

    @Mock
    private UserPreferencesRepository userPreferencesRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserPreferencesService userPreferencesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void updateUserPreferences_shouldSavePreferencesForUser() {
        // Arrange
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testUser");
        User mockUser = new User(); // Assuming you have a User entity
        when(userService.getUserByUsername("testUser")).thenReturn(mockUser);
        List<String> townNames = Arrays.asList("Vilnius", "Kaunas");

        // Act
        userPreferencesService.updateUserPreferences(townNames, mockPrincipal);

        // Assert
        verify(userPreferencesRepository).deleteByUser(mockUser); // Ensure old preferences are deleted
        verify(userPreferencesRepository, times(2)).save(any(User_Preferences.class)); // Ensure new ones are saved
    }

    @Test
    void updateUserPreferences_shouldThrowRuntimeExceptionOnTransactionFailure() {
        // Arrange
        Principal mockPrincipal = mock(Principal.class);
        when(mockPrincipal.getName()).thenReturn("testUser");
        User mockUser = new User();
        when(userService.getUserByUsername("testUser")).thenReturn(mockUser);
        List<String> townNames = Arrays.asList("Vilnius", "Kaunas");

        // Simulate a TransactionSystemException
        when(userPreferencesRepository.save(any(User_Preferences.class)))
                .thenThrow(new TransactionSystemException("Transaction error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userPreferencesService.updateUserPreferences(townNames, mockPrincipal);
        });

        assertEquals("Transaction failed: Transaction error", thrown.getMessage());
        verify(userPreferencesRepository).deleteByUser(mockUser); // Ensure preferences were deleted before the exception
    }

    @Test
    void getUserPreferencesByUserId_shouldReturnUserPreferences() {
        // Arrange
        Long userId = 1L;
        List<User_Preferences> mockPreferences = Arrays.asList(
                new User_Preferences(new User(), "Vilnius"),
                new User_Preferences(new User(), "Kaunas")
        );
        when(userPreferencesRepository.findByUserId(userId)).thenReturn(mockPreferences);

        // Act
        List<User_Preferences> result = userPreferencesService.getUserPreferencesByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(userPreferencesRepository).findByUserId(userId); // Ensure the repository was queried with the correct ID
    }

    @Test
    void deleteUserPreference_shouldDeleteById() {
        // Arrange
        Long preferenceId = 123L;

        // Act
        userPreferencesService.deleteUserPreference(preferenceId);

        // Assert
        verify(userPreferencesRepository).deleteById(preferenceId); // Ensure the preference is deleted by ID
    }
}
