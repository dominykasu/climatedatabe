package lt.ca.javau10.climatedata;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.repositories.DataSnapshotsRepository;
import lt.ca.javau10.climatedata.services.DataSnapshotsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataSnapshotsServiceTests {

    @Mock
    private DataSnapshotsRepository dataSnapshotsRepository;

    @InjectMocks
    private DataSnapshotsService dataSnapshotsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDataSnapshotsTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Data_Snapshots snapshot1 = new Data_Snapshots("region1", "metric1", 12.00, LocalDate.now());
        Data_Snapshots snapshot2 = new Data_Snapshots("region2", "metric2", 13.00, LocalDate.now());

        when(dataSnapshotsRepository.findAll()).thenReturn(Arrays.asList(snapshot1, snapshot2));

        // Act
        List<Data_Snapshots> snapshots = dataSnapshotsService.getAllDataSnapshots();

        // Assert
        assertEquals(2, snapshots.size());
        verify(dataSnapshotsRepository, times(1)).findAll();
    }

    @Test
    void createDataSnapshotTest() {
        // Arrange
        Data_Snapshots snapshot = new Data_Snapshots("region", "metric", 12.00, LocalDate.now());

        when(dataSnapshotsRepository.save(any(Data_Snapshots.class))).thenReturn(snapshot);

        // Act
        Data_Snapshots createdSnapshot = dataSnapshotsService.createDataSnapshot(snapshot);

        // Assert
        assertNotNull(createdSnapshot);
        assertEquals("region", createdSnapshot.getRegion());
        verify(dataSnapshotsRepository, times(1)).save(snapshot);
    }

    @Test
    void getDataSnapshotByIdTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Data_Snapshots snapshot = new Data_Snapshots("region", "metric", 12.00, LocalDate.now());

        when(dataSnapshotsRepository.findById(1L)).thenReturn(Optional.of(snapshot));

        // Act
        Data_Snapshots foundSnapshot = dataSnapshotsService.getDataSnapshotById(1L);

        // Assert
        assertNotNull(foundSnapshot);
        assertEquals("region", foundSnapshot.getRegion());
        verify(dataSnapshotsRepository, times(1)).findById(1L);
    }

    @Test
    void deleteDataSnapshotTest() {
        // Arrange
        User user = new User("username", "email", "password", "role");
        Data_Snapshots snapshot = new Data_Snapshots("region", "metric", 12.00, LocalDate.now());

        when(dataSnapshotsRepository.findById(1L)).thenReturn(Optional.of(snapshot));
        doNothing().when(dataSnapshotsRepository).delete(snapshot);

        // Act
        dataSnapshotsService.deleteDataSnapshot(1L);

        // Assert
        verify(dataSnapshotsRepository, times(1)).deleteById(1L);
    }
}