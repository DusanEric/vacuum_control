package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Vacuum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vacuumId;

    @Column
    private Status status;

    @ManyToOne
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "EMAIL")
    private User addedBy;

    @Column
    private boolean active;

    @Column
    private Integer count = 0;

    @Version
    private Integer version = 0;

    @OneToMany(mappedBy = "vacuum", cascade = CascadeType.ALL)
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
    private List<ErrorMessage> errorMessageList;

    @OneToMany(mappedBy = "vacuum", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<ScheduledTask> scheduledTaskList;

}
