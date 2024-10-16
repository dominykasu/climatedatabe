package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.User_Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferencesRepository extends JpaRepository<User_Preferences, Long> {
}
