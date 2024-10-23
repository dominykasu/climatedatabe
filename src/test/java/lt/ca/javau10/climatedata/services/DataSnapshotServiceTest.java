package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.repositories.DataSnapshotsRepository;
import lt.ca.javau10.climatedata.services.DataSnapshotsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DataSnapshotsServiceTest {

    @Mock
    private DataSnapshotsRepository dataSnapshotsRepository;

    @InjectMocks
    private DataSnapshotsService dataSnapshotsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDataSnapshots() {
        // Arrange
        List<Data_Snapshots> snapshotList = new ArrayList<>();
        snapshotList.add(new Data_Snapshots());
        when(dataSnapshotsRepository.findAll()).thenReturn(snapshotList);

        // Act
        List<Data_Snapshots> result = dataSnapshotsService.getAllDataSnapshots();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dataSnapshotsRepository, times(1)).findAll();
    }

    @Test
    void testCreateDataSnapshot() {
        // Arrange
        Data_Snapshots snapshot = new Data_Snapshots();
        when(dataSnapshotsRepository.save(any(Data_Snapshots.class))).thenReturn(snapshot);

        // Act
        Data_Snapshots result = dataSnapshotsService.createDataSnapshot(snapshot);

        // Assert
        assertNotNull(result);
        verify(dataSnapshotsRepository, times(1)).save(snapshot);
    }

    @Test
    void testGetDataSnapshotById() {
        // Arrange
        Data_Snapshots snapshot = new Data_Snapshots();
        when(dataSnapshotsRepository.findById(1L)).thenReturn(Optional.of(snapshot));

        // Act
        Data_Snapshots result = dataSnapshotsService.getDataSnapshotById(1L);

        // Assert
        assertNotNull(result);
        verify(dataSnapshotsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDataSnapshotById_NotFound() {
        // Arrange
        when(dataSnapshotsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Data_Snapshots result = dataSnapshotsService.getDataSnapshotById(1L);

        // Assert
        assertNull(result);
        verify(dataSnapshotsRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSnapshotsByUser() {
        // Arrange
        User user = new User();
        List<Data_Snapshots> snapshots = new ArrayList<>();
        snapshots.add(new Data_Snapshots());
        when(dataSnapshotsRepository.findByUser(user)).thenReturn(snapshots);

        // Act
        List<Data_Snapshots> result = dataSnapshotsService.getSnapshotsByUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(dataSnapshotsRepository, times(1)).findByUser(user);
    }

    @Test
    void testDeleteDataSnapshot() {
        // Act
        dataSnapshotsService.deleteDataSnapshot(1L);

        // Assert
        verify(dataSnapshotsRepository, times(1)).deleteById(1L);
    }
}

