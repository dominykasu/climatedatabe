package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}


