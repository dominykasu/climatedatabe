package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import lt.ca.javau10.climatedata.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSnapshotsRepository extends JpaRepository<Data_Snapshots, Long> {
    List<Data_Snapshots> findByUser(User user);
}