package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPreferencesRepository extends JpaRepository<User_Preferences, Long> {
    void deleteByUser(User user);
    List<User_Preferences> findByUserId(Long userId);
}
