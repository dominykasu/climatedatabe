package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.Data_Snapshots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSnapshotsRepository extends JpaRepository<Data_Snapshots, Long> {
}
