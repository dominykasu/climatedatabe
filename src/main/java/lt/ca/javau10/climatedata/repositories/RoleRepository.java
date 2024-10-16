package lt.ca.javau10.climatedata.repositories;

import lt.ca.javau10.climatedata.entities.ERole;
import lt.ca.javau10.climatedata.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

