package lt.ca.javau10.climatedata.utils;

import lt.ca.javau10.climatedata.entities.Role;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.UserDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EntityMapper {

    public User toUserEntity(UserDto dto) {

        User entity = new User();
        entity.setId( dto.getId());
        entity.setUsername( dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }
    //Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {

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
