package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.Vacuum;

import java.util.List;

@Repository
public interface VacuumRepository extends JpaRepository<Vacuum, Long> {

    List<Vacuum> findVacuumsByAddedBy_Email(String email);

    List<Vacuum> findVacuumsByStatusAndAddedBy_Email(Status status, String email);

}
