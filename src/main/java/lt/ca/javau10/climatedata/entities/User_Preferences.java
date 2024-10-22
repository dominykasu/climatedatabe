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

    public User_Preferences(){};
    public User_Preferences(User user, String preferredRegion) {
        this.user = user;
        this.preferred_region = preferredRegion;
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

}
