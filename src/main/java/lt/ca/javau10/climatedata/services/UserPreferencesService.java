package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.entities.User_Preferences;
import lt.ca.javau10.climatedata.repositories.UserPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPreferencesService {
    @Autowired
    private UserPreferencesRepository userPreferencesRepository;

    public List<User_Preferences> getAllUserPreferences() {
        return userPreferencesRepository.findAll();
    }

    public User_Preferences createUserPreference(User_Preferences userPreference) {
        return userPreferencesRepository.save(userPreference);
    }

    public User_Preferences getUserPreferenceById(Long id) {
        return userPreferencesRepository.findById(id).orElse(null);
    }

    public void deleteUserPreference(Long id) {
        userPreferencesRepository.deleteById(id);
    }
}
