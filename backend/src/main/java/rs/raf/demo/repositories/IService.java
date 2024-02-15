package rs.raf.demo.repositories;

import rs.raf.demo.model.*;

import java.util.List;
import java.util.Optional;

public interface IService <T, ID> {
    <S extends T> S save(S var1);

    Optional<T> findById(ID var1);

    List<T> findAll();

    void deleteById(ID var1);

    List<Vacuum> findVacuumsByAddedBy(String name);

    List<ErrorMessage> findMessagesByVacuumID(Long id);

    List<ScheduledTask> findTasksByUserAndVacuumID(User user, Vacuum vacuum);

    List<Vacuum> findVacuumsByStatusAndAddedBy(Status status, String email);
}
