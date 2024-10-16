package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.entities.Alerts;
import lt.ca.javau10.climatedata.services.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertsController {
    @Autowired
    private AlertsService alertsService;


    @GetMapping("/all")
    public List<Alerts> getAllAlerts() {
        return alertsService.getAllAlerts();
    }

    @PostMapping("/create")
    public Alerts createAlert(@RequestBody Alerts alert) {
        return alertsService.createAlert(alert);
    }

    @GetMapping("/{id}")
    public Alerts getAlert(@PathVariable Long id) {
        return alertsService.getAlertById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAlert(@PathVariable Long id) {
        alertsService.deleteAlert(id);
    }
}
