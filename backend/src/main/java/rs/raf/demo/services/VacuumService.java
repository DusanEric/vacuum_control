package rs.raf.demo.services;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.IService;
import rs.raf.demo.repositories.VacuumRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class VacuumService implements IService<Vacuum, Long> {

    private final VacuumRepository vacuumRepository;

    public VacuumService(VacuumRepository vacuumRepository) {
        this.vacuumRepository = vacuumRepository;
    }

    @Override
    public Vacuum save(Vacuum teacher) {
        return vacuumRepository.save(teacher);
    }

    @Override
    public Optional<Vacuum> findById(Long id) {
        return vacuumRepository.findById(id);
    }

    @Override
    public List<Vacuum> findAll() {
        return (List<Vacuum>) vacuumRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        vacuumRepository.deleteById(id);
    }

    @Override
    public List<Vacuum> findVacuumsByAddedBy(String email){

        return vacuumRepository.findVacuumsByAddedBy_Email(email);
    }
    @Override
    public List<Vacuum> findVacuumsByStatusAndAddedBy(Status status, String email){
        return vacuumRepository.findVacuumsByStatusAndAddedBy_Email(status,email);
    }

    @Override
    public List<ErrorMessage> findMessagesByVacuumID(Long id) {
        return null;
    }

    @Override
    public List<ScheduledTask> findTasksByUserAndVacuumID(User user, Vacuum vacuumId) {
        return null;
    }


//    public void start(Long id){
//        Optional<Vacuum> vacuumOptional = this.vacuumRepository.findById(id);
//        Vacuum vacuum = vacuumOptional.get();
//        Vacuum updatedVacuum = new Vacuum();
//
//        if (vacuum.getStatus() == Status.OFF) {
//            try {
//                Thread.sleep(15000);
//
//                System.out.println("STARTING VACUUM " + id + " FROM SERVICE...");
//                updatedVacuum.setVacuumId(vacuum.getVacuumId());
//                updatedVacuum.setStatus(Status.ON);
//                updatedVacuum.setAddedBy(vacuum.getAddedBy());
//                updatedVacuum.setActive(true);
//                updatedVacuum.setVersion(vacuum.getVersion());
//                Integer cnt = vacuum.getCount();
//                System.out.println("COUNT ZA " + id + " " + cnt);
//                updatedVacuum.setCount(cnt + 1);
//
//                this.vacuumRepository.save(updatedVacuum);
//
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ObjectOptimisticLockingFailureException exception) {
//                this.start(id);
//            }
//        } else {
//            throw new AccessDeniedException("Forbidden");
//        }
//    }

    //ZA ASYNC
    public CompletableFuture<Void> start(Long id) {
        Optional<Vacuum> vacuumOptional = this.vacuumRepository.findById(id);
        Vacuum vacuum = vacuumOptional.orElseThrow(() -> new RuntimeException("Vacuum not found"));

        if (vacuum.getStatus() == Status.OFF) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(15000);

                    System.out.println("STARTING VACUUM " + id + " FROM SERVICE...");
                    Vacuum updatedVacuum = new Vacuum();
                    updatedVacuum.setVacuumId(vacuum.getVacuumId());
                    updatedVacuum.setStatus(Status.ON);
                    updatedVacuum.setAddedBy(vacuum.getAddedBy());
                    updatedVacuum.setActive(true);
                    updatedVacuum.setVersion(vacuum.getVersion());
                    Integer cnt = vacuum.getCount();
                    System.out.println("COUNT FOR " + id + ": " + cnt);
                    updatedVacuum.setCount(cnt + 1);

                    this.vacuumRepository.save(updatedVacuum);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ObjectOptimisticLockingFailureException exception) {
                    this.start(id);
                }
            });
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }

    public CompletableFuture<Void> stop(Long id){
        Optional<Vacuum> vacuumOptional = this.vacuumRepository.findById(id);
        Vacuum vacuum = vacuumOptional.get();
        Vacuum updatedVacuum = new Vacuum();

        if (vacuum.getStatus() == Status.ON) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(15000);


                    updatedVacuum.setVacuumId(vacuum.getVacuumId());
                    updatedVacuum.setStatus(Status.OFF);
                    updatedVacuum.setAddedBy(vacuum.getAddedBy());
                    updatedVacuum.setActive(true);
                    updatedVacuum.setVersion(vacuum.getVersion());
                    updatedVacuum.setCount(vacuum.getCount());

                    this.vacuumRepository.save(updatedVacuum);

                    if (updatedVacuum.getCount() == 3) {
                        discharging(id);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }else {
                throw new AccessDeniedException("Forbidden");
        }
    }

    public CompletableFuture<Void> discharging(Long id) {
        Optional<Vacuum> vacuumOptional = this.vacuumRepository.findById(id);
        Vacuum vacuum = vacuumOptional.get();
        Vacuum updatedVacuum = new Vacuum();
        System.out.println("DISCHARGING VACUUM " + id + " FROM SERVICE...");

        if (vacuum.getStatus() == Status.OFF) {
            return CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(15000);
                    updatedVacuum.setVacuumId(vacuum.getVacuumId());
                    updatedVacuum.setStatus(Status.DISCHARGING);
                    updatedVacuum.setAddedBy(vacuum.getAddedBy());
                    updatedVacuum.setActive(true);
                    updatedVacuum.setVersion(vacuum.getVersion());
                    updatedVacuum.setCount(vacuum.getCount());

                    this.vacuumRepository.save(updatedVacuum);

                    System.out.println("SETTING OFF STATUS FOR VACUUM: " + id);
                    Thread.sleep(15000);
                    updatedVacuum.setVacuumId(vacuum.getVacuumId());
                    updatedVacuum.setStatus(Status.OFF);
                    updatedVacuum.setAddedBy(vacuum.getAddedBy());
                    updatedVacuum.setActive(true);
                    updatedVacuum.setVersion(vacuum.getVersion());
                    updatedVacuum.setCount(0);

                    this.vacuumRepository.save(updatedVacuum);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }else {
            throw new AccessDeniedException("Forbidden");
        }
    }

}
