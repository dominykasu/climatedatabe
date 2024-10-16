package lt.ca.javau10.climatedata.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name="alerts")
public class Alerts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String metric;
    private Double threshold;
    private LocalDateTime lastAlertedAt;

    public Alerts(){};
    public Alerts(User user, String metric, Double threshold, LocalDateTime lastAlertedAt) {
        this.user = user;
        this.metric = metric;
        this.threshold = threshold;
        this.lastAlertedAt = lastAlertedAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public LocalDateTime getLastAlertedAt() {
        return lastAlertedAt;
    }

    public void setLastAlertedAt(LocalDateTime lastAlertedAt) {
        this.lastAlertedAt = lastAlertedAt;
    }
}
