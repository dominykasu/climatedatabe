package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.entities.Role;
import lt.ca.javau10.climatedata.repositories.UserRepository;
import lt.ca.javau10.climatedata.repositories.RoleRepository;  // Add RoleRepository
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.ERole;  // For enum role checking
import lt.ca.javau10.climatedata.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final EntityMapper entityMapper;

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;  // Add role repository

    public UserService(UserRepository userRepository, EntityMapper entityMapper) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by id
    public static User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);  // Or throw a custom exception for not found.
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            // Only update the password if it's provided
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(updatedUser.getPassword());
            }

            // Handle the role update
            Set<Role> updatedRoles = new HashSet<>();
            for (Role role : updatedUser.getRole()) {
                // Convert role name (String) to ERole enum
                ERole roleEnum = ERole.valueOf(role.getName()); // Convert string to enum
                Role existingRole = roleRepository.findByName(roleEnum)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + role.getName()));
                updatedRoles.add(existingRole);
            }
            existingUser.setRole(updatedRoles);  // Assign fetched roles from DB

            return userRepository.save(existingUser);
        }
        return null;  // Or throw an exception if user not found
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        logger.info("Loaded :"+user.toString());
        return entityMapper.toUserDto(user);
    }
}