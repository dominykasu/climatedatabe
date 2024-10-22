package lt.ca.javau10.climatedata.services;

import jakarta.transaction.Transactional;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.repositories.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPreferencesService {

    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public List<User_Preferences> updateUserPreferences(List<String> townNames, Principal principal) {
        try {
            User user = userService.getUserByUsername(principal.getName());

            userPreferencesRepository.deleteByUser(user);

            List<User_Preferences> savedPreferences = new ArrayList<>();
            for (String townName : townNames) {
                User_Preferences newPreference = new User_Preferences(user, townName);
                savedPreferences.add(userPreferencesRepository.save(newPreference));
            }
            return savedPreferences;
        } catch (TransactionSystemException e) {
            throw new RuntimeException("Transaction failed: " + e.getMessage());
        }
    }
    public List<User_Preferences> getUserPreferencesByUserId(Long userId) {
        return userPreferencesRepository.findByUserId(userId);
    }

    public void deleteUserPreference(Long id) {
        userPreferencesRepository.deleteById(id);
    }
}