package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService climateService;
    @CrossOrigin(origins = "http://localhost:3000")
    // Get all users (Read)
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return climateService.getAllUsers();
    }

    // Get a single user by id (Read)
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return climateService.getUserById(id);
    }

    // Create a new user (Create)
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return climateService.createUser(user);
    }

    // Update an existing user (Update)
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return climateService.updateUser(id, user);
    }

    // Delete a user (Delete)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        climateService.deleteUser(id);
    }
}