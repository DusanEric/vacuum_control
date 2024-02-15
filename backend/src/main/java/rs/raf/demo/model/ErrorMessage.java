package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ErrorMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long errorMessageId;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "VACUUM_ID", referencedColumnName = "vacuumId")
    private Vacuum vacuum;

    @Column
    private String operation;

    @Column
    private String message;
}
