package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.services.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/preferences")
public class UserPreferencesController {
    @Autowired
    private UserPreferencesService userPreferencesService;
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public List<User_Preferences> getAllPreferences() {
        return userPreferencesService.getAllUserPreferences();
    }

    @PostMapping("/create")
    public User_Preferences createUserPreference(@RequestBody User_Preferences userPreference) {
        return userPreferencesService.createUserPreference(userPreference);
    }

    @GetMapping("/{id}")
    public User_Preferences getUserPreference(@PathVariable Long id) {
        return userPreferencesService.getUserPreferenceById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserPreference(@PathVariable Long id) {
        userPreferencesService.deleteUserPreference(id);
    }
}
