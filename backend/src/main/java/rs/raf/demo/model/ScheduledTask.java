package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ScheduledTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduledTaskId;

    @ManyToOne
    @JoinColumn(name = "VACUUM_ID", referencedColumnName = "vacuumId")
    private Vacuum vacuum;

    @ManyToOne
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "EMAIL")
    private User user;

    @Column
    private LocalDateTime date;

    @Column
    private String operation;

}
