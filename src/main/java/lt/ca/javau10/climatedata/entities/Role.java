package lt.ca.javau10.climatedata.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public Role() {

    }
    public Role(ERole name) {
        this.name = name;
    }

    public Role(String roleUser) {
    }

    public String getName() {
        return name.toString();
    }

    public void setName(ERole eRole) {
        this.name = eRole;
    }

    public void setId(long l) {
        this.id = l;
    }
}
