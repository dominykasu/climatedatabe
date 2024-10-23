package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.services.DataSnapshotsService;
import lt.ca.javau10.climatedata.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataSnapshotsControllerTest {

    @Mock
    private DataSnapshotsService dataSnapshotsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private DataSnapshotsController dataSnapshotsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDataSnapshot_shouldReturnSnapshot_whenDataIsValid() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("region", "Vilnius");
        payload.put("metric", "Temperature");
        payload.put("value", List.of(Map.of("date", "2024-10-18", "value", 15)));
        payload.put("username", "testUser");

        User user = new User();
        user.setUsername("testUser");

        Data_Snapshots snapshot = new Data_Snapshots("Vilnius", "Temperature", List.of(), LocalDate.now(), user);

        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(dataSnapshotsService.createDataSnapshot(any(Data_Snapshots.class))).thenReturn(snapshot);

        Data_Snapshots result = dataSnapshotsController.createDataSnapshot(payload);

        assertNotNull(result);
        assertEquals("Vilnius", result.getRegion());
    }

    @Test
    void getSnapshotsByUsername_shouldReturnList_whenUserExists() {
        User user = new User();
        user.setUsername("testUser");

        List<Data_Snapshots> snapshots = List.of(new Data_Snapshots());

        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(dataSnapshotsService.getSnapshotsByUser(user)).thenReturn(snapshots);

        List<Data_Snapshots> result = dataSnapshotsController.getSnapshotsByUsername("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
