package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String permissions;

    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.ALL)
    /**
     * @JsonIgnore govori Jackson-u da ne upisuje ovaj
     * atribut u JSON response.
     */
    @JsonIgnore
    /**
     * @ToString.Exclude govori Lombok-u da ne ukljuci ovaj
     * atribut u toString().
     */
    @ToString.Exclude
    private List<Vacuum> vacuums;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<ScheduledTask> scheduledTaskList;
}
