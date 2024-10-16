package lt.ca.javau10.climatedata;

import lt.ca.javau10.climatedata.entities.Alerts;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.repositories.AlertsRepository;
import lt.ca.javau10.climatedata.services.AlertsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertsServiceTests {

    @Mock
    private AlertsRepository alertsRepository;

    @InjectMocks
    private AlertsService alertsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAlertsTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Alerts alert1 = new Alerts(user, "metric", 12.00, LocalDateTime.now());
        Alerts alert2 = new Alerts(user, "metric1", 13.00, LocalDateTime.now());

        when(alertsRepository.findAll()).thenReturn(Arrays.asList(alert1, alert2));

        // Act
        List<Alerts> alerts = alertsService.getAllAlerts();

        // Assert
        assertEquals(2, alerts.size());
        verify(alertsRepository, times(1)).findAll();
    }

    @Test
    void createAlertTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Alerts alert = new Alerts(user, "metric", 12.00, LocalDateTime.now());

        when(alertsRepository.save(any(Alerts.class))).thenReturn(alert);

        // Act
        Alerts createdAlert = alertsService.createAlert(alert);

        // Assert
        assertNotNull(createdAlert);
        assertEquals("metric", createdAlert.getMetric());
        verify(alertsRepository, times(1)).save(alert);
    }

    @Test
    void getAlertByIdTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Alerts alert = new Alerts(user, "metric", 12.00, LocalDateTime.now());

        when(alertsRepository.findById(1L)).thenReturn(Optional.of(alert));

        // Act
        Alerts foundAlert = alertsService.getAlertById(1L);

        // Assert
        assertNotNull(foundAlert);
        assertEquals("metric", foundAlert.getMetric());
        verify(alertsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteAlertTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Alerts alert = new Alerts(user, "metric", 12.00, LocalDateTime.now());
        Long alertId = 1L;

        when(alertsRepository.findById(alertId)).thenReturn(Optional.of(alert));
        doNothing().when(alertsRepository).delete(alert);

        // Act
        alertsService.deleteAlert(alertId);

        // Assert
        verify(alertsRepository, times(1)).deleteById(alertId);
    }
}