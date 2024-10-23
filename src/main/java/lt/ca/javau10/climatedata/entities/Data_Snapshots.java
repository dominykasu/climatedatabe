package lt.ca.javau10.climatedata.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @Column(columnDefinition = "TEXT")
    @JsonIgnore  // Prevent the raw JSON string from being sent in the response
    private String valuesJson;

    private LocalDate snapshotDate;

    public Data_Snapshots() {
    }

    public Data_Snapshots(String region, String metric, List<Map<String, Object>> values, LocalDate snapshotDate, User user) {
        this.region = region;
        this.metric = metric;
        this.setValues(values);  // Use the setValues method to serialize the values list to JSON
        this.snapshotDate = snapshotDate;
        this.user = user;
    }

    // Use @JsonProperty to ensure values are serialized and deserialized as JSON
    @JsonProperty("values")
    public List<Map<String, Object>> getValues() {
        if (this.valuesJson == null || this.valuesJson.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.valuesJson, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize values from JSON: " + this.valuesJson, e);
        }
    }

    @JsonProperty("values")
    public void setValues(List<Map<String, Object>> values) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.valuesJson = mapper.writeValueAsString(values);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize values", e);
        }
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
    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }
    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }
}