package lt.ca.javau10.climatedata.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_preferences")
public class User_Preferences {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String preferred_region;
    private String preferred_metrics;
    private String time_range;

    public User_Preferences(){};
    public User_Preferences(User user, String preferred_region, String preferred_metrics, String time_range) {
        this.user = user;
        this.preferred_region = preferred_region;
        this.preferred_metrics = preferred_metrics;
        this.time_range = time_range;
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

    public String getPreferredRegion() {
        return preferred_region;
    }

    public void setPreferredRegion(String preferred_region) {
        this.preferred_region = preferred_region;
    }

    public String getPreferredMetrics() {
        return preferred_metrics;
    }

    public void setPreferredMetrics(String preferred_metrics) {
        this.preferred_metrics = preferred_metrics;
    }

    public String getTimeRange() {
        return time_range;
    }

    public void setTimeRange(String time_range) {
        this.time_range = time_range;
    }
}
