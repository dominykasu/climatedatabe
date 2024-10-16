package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.repositories.UserRepository;
import lt.ca.javau10.climatedata.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by id
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);  // Or throw a custom exception for not found.
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(Long id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existingUser = user.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }
        return null;  // Or throw an exception if user not found
    }

    // Delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}