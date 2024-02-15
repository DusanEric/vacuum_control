package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.ScheduledTask;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.services.ScheduledTaskService;
import rs.raf.demo.services.UserService;
import rs.raf.demo.services.VacuumService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/tasks")
public class ScheduledTaskRestController {

    private final ScheduledTaskService scheduledTaskService;

    private final UserService userService;

    private final VacuumService vacuumService;

    public ScheduledTaskRestController(ScheduledTaskService scheduledTaskService, UserService userService, VacuumService vacuumService) {
        this.scheduledTaskService = scheduledTaskService;
        this.userService = userService;
        this.vacuumService = vacuumService;
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ScheduledTask> getAllTasks(@PathVariable("id") Long id, Authentication authentication){

        String userEmail = authentication.getName();
        System.out.println("EMAIL USERA IZ TASKOVA: " + userEmail);
        User user = userService.loadUserByEmail(userEmail);

        Optional<Vacuum> vacuumOptional = vacuumService.findById(id);

        Vacuum vacuum = vacuumOptional.get();

        List<ScheduledTask> scheduledTasks = scheduledTaskService.findTasksByUserAndVacuumID(user, vacuum);
        return scheduledTasks;
    }

    @PostMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ScheduledTask createTask(@RequestBody ScheduledTask scheduledTask,@PathVariable("id") Long id, Authentication authentication){

        String userEmail = authentication.getName();
        System.out.println("EMAIL USERA IZ TASKOVA: " + userEmail);
        User user = userService.loadUserByEmail(userEmail);

        Optional<Vacuum> vacuumOptional = vacuumService.findById(id);

        Vacuum vacuum = vacuumOptional.get();

        ScheduledTask updatedST = new ScheduledTask();
        updatedST.setVacuum(vacuum);
        updatedST.setUser(user);
        updatedST.setDate(scheduledTask.getDate());
        updatedST.setOperation(scheduledTask.getOperation());
        return scheduledTaskService.save(updatedST);
    }
}
