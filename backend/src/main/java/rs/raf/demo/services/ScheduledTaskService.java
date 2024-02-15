package rs.raf.demo.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.IService;
import rs.raf.demo.repositories.ScheduledTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledTaskService implements IService<ScheduledTask, Long> {

    private final ScheduledTaskRepository scheduledTaskRepository;
    private final VacuumService vacuumService;

    public ScheduledTaskService(ScheduledTaskRepository scheduledTaskRepository, VacuumService vacuumService) {
        this.scheduledTaskRepository = scheduledTaskRepository;
        this.vacuumService = vacuumService;
    }

    @Override
    public <S extends ScheduledTask> S save(S var1) {
        return scheduledTaskRepository.save(var1);
    }

    @Override
    public Optional<ScheduledTask> findById(Long var1) {
        return scheduledTaskRepository.findById(var1);
    }

    @Override
    public List<ScheduledTask> findAll() {
        return (List<ScheduledTask>) scheduledTaskRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        scheduledTaskRepository.deleteById(var1);
    }

    @Override
    public List<Vacuum> findVacuumsByAddedBy(String name) {
        return null;
    }

    @Override
    public List<ErrorMessage> findMessagesByVacuumID(Long id) {
        return null;
    }

    @Override
    public List<ScheduledTask> findTasksByUserAndVacuumID(User user, Vacuum vacuum) {


        return scheduledTaskRepository.findScheduledTasksByUserAndVacuum(user, vacuum);
    }

    @Override
    public List<Vacuum> findVacuumsByStatusAndAddedBy(Status status, String email) {
        return null;
    }

    //PROVERAVA SVAKI MINUT
    @Scheduled(fixedRate = 60000)
    public void checkScheduledTasks() {
        System.out.println("CHECKING...");
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//        System.out.println("VREME: " + currentDateTime);

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startRange = currentDateTime;
        LocalDateTime endRange = currentDateTime.plusMinutes(1);
        System.out.println("VREME START: " + startRange);
        System.out.println("VREME CURRENT: " + currentDateTime);
        System.out.println("VREME END: " + endRange);

        List<ScheduledTask> tasksToExecute = scheduledTaskRepository.findScheduledTasksByDateBetween(endRange,startRange);

        for (ScheduledTask task : tasksToExecute) {
            executeTask(task);
        }
    }

    private void executeTask(ScheduledTask task) {
        System.out.println("EXECUTING...");

//        if (task.getOperation().equals("START")){
//            vacuumService.start(task.getVacuum().getVacuumId());
//        }

        switch (task.getOperation()) {
            case "START":
                vacuumService.start(task.getVacuum().getVacuumId());
                break;
            case "STOP":
                vacuumService.stop(task.getVacuum().getVacuumId());
                break;
            case "discharge":
                vacuumService.discharging(task.getVacuum().getVacuumId());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported operation: " + task.getOperation());
        }

        scheduledTaskRepository.delete(task);
    }
}
