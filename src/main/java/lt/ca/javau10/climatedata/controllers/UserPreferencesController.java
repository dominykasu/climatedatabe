package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.repositories.UserPreferencesRepository;
import lt.ca.javau10.climatedata.services.UserPreferencesService;
import lt.ca.javau10.climatedata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user/preferences")
public class UserPreferencesController {
    @Autowired
    private UserPreferencesService userPreferencesService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserPreferencesRepository userPreferencesRepository;
    @GetMapping("/all")
    public ResponseEntity<List<User_Preferences>> getAllPreferences(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<User_Preferences> preferences = userPreferencesService.getUserPreferencesByUserId(user.getId());
        return ResponseEntity.ok(preferences);
    }

    @PostMapping("/create")
    public List<User_Preferences> createUserPreferences(@RequestBody List<String> townNames, Principal principal) {
        return userPreferencesService.updateUserPreferences(townNames, principal);
    }

    @DeleteMapping("/{id}")
    public void deleteUserPreference(@PathVariable Long id) {
        userPreferencesService.deleteUserPreference(id);
    }
}
