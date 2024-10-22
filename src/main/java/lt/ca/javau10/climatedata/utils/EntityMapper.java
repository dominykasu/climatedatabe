package lt.ca.javau10.climatedata.utils;

import lt.ca.javau10.climatedata.entities.Role;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EntityMapper {

    public UserDto toUserDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                (Set<Role>) entity.getRoles()
        );
    }

}
