package lt.ca.javau10.climatedata.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "data_snapshots")
public class Data_Snapshots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String region;
    private String metric;
    private Double value;
    private LocalDate snapshotDate;

    public Data_Snapshots(){};
    public Data_Snapshots(String region, String metric, Double value, LocalDate snapshotDate) {
        this.region = region;
        this.metric = metric;
        this.value = value;
        this.snapshotDate = snapshotDate;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}
