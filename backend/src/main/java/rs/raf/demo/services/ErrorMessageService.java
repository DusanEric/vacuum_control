package rs.raf.demo.services;

import org.springframework.stereotype.Service;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.ErrorMessageRepository;
import rs.raf.demo.repositories.IService;

import java.util.List;
import java.util.Optional;

@Service
public class ErrorMessageService implements IService<ErrorMessage, Long> {

    private final ErrorMessageRepository errorMessageRepository;

    public ErrorMessageService(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    @Override
    public <S extends ErrorMessage> S save(S teacher) {
        return errorMessageRepository.save(teacher);
    }

    @Override
    public Optional<ErrorMessage> findById(Long id) {
        return errorMessageRepository.findById(id);
    }

    @Override
    public List<ErrorMessage> findAll() {
        return (List<ErrorMessage>) errorMessageRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        errorMessageRepository.deleteById(id);
    }

    @Override
    public List<Vacuum> findVacuumsByAddedBy(String name) {
        return null;
    }

    @Override
    public List<ErrorMessage> findMessagesByVacuumID(Long id){

        return errorMessageRepository.findErrorMessagesByVacuum(id);
    }

    @Override
    public List<ScheduledTask> findTasksByUserAndVacuumID(User user, Vacuum vacuumId) {
        return null;
    }

    @Override
    public List<Vacuum> findVacuumsByStatusAndAddedBy(Status status, String email) {
        return null;
    }

}
