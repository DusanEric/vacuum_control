package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.ScheduledTask;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Vacuum;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ScheduledTaskRepository extends JpaRepository<ScheduledTask, Long> {


//    List<ScheduledTask> findScheduledTasksByDate(LocalDateTime localDateTime);

    List<ScheduledTask> findScheduledTasksByDateBetween(LocalDateTime start, LocalDateTime end);

    List<ScheduledTask> findScheduledTasksByUserAndVacuum(User user, Vacuum vacuum);

}
