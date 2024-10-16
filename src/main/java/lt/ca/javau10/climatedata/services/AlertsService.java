package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.entities.Alerts;
import lt.ca.javau10.climatedata.repositories.AlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertsService {
    @Autowired
    private AlertsRepository alertsRepository;

    public List<Alerts> getAllAlerts() {
        return alertsRepository.findAll();
    }

    public Alerts createAlert(Alerts alert) {
        return alertsRepository.save(alert);
    }

    public Alerts getAlertById(Long id) {
        return alertsRepository.findById(id).orElse(null);
    }

    public void deleteAlert(Long id) {
        alertsRepository.deleteById(id);
    }
}
