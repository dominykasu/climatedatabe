package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.repositories.DataSnapshotsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSnapshotsService {

    @Autowired
    private DataSnapshotsRepository dataSnapshotsRepository;

    public List<Data_Snapshots> getAllDataSnapshots() {
        return dataSnapshotsRepository.findAll();
    }

    public Data_Snapshots createDataSnapshot(Data_Snapshots dataSnapshot) {
        return dataSnapshotsRepository.save(dataSnapshot);  // Saves the snapshot along with the JSON values
    }

    public Data_Snapshots getDataSnapshotById(Long id) {
        return dataSnapshotsRepository.findById(id).orElse(null);
    }
    public List<Data_Snapshots> getSnapshotsByUser(User user) {
        return dataSnapshotsRepository.findByUser(user);
    }
    public void deleteDataSnapshot(Long id) {
        dataSnapshotsRepository.deleteById(id);
    }
}