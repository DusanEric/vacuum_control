package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;
import rs.raf.demo.model.Vacuum;
import rs.raf.demo.services.UserService;
import rs.raf.demo.services.VacuumService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@CrossOrigin
@RestController
@RequestMapping("/api/vacuums")
public class VacuumRestController {

    private final VacuumService vacuumService;
    private final UserService userService;

    public VacuumRestController(VacuumService vacuumService, UserService userService) {
        this.vacuumService = vacuumService;
        this.userService = userService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vacuum> getAllVacuums(Authentication authentication){

        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_search_vacuum")) {
                String userEmail = authentication.getName();

                UserDetails loggedInUser = userService.loadUserByUsername(userEmail);

                List<Vacuum> userVacuums = vacuumService.findVacuumsByAddedBy(loggedInUser.getUsername());

                return userVacuums;
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @GetMapping(value = "/all/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vacuum> getAllVacuumsWithStatus(@PathVariable("status") String string, Authentication authentication){

        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_search_vacuum")) {
                String userEmail = authentication.getName();

                UserDetails loggedInUser = userService.loadUserByUsername(userEmail);

                if(string.equals("ON")){
                    List<Vacuum> userVacuums = vacuumService.findVacuumsByStatusAndAddedBy(Status.ON,loggedInUser.getUsername());
                    return userVacuums;
                }else if(string.equals("OFF")){
                    List<Vacuum> userVacuums = vacuumService.findVacuumsByStatusAndAddedBy(Status.OFF,loggedInUser.getUsername());
                    return userVacuums;
                } else if(string.equals("DISCHARGING")){
                    List<Vacuum> userVacuums = vacuumService.findVacuumsByStatusAndAddedBy(Status.DISCHARGING,loggedInUser.getUsername());
                    return userVacuums;
                }

//                return userVacuums;
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVacuumById(@RequestParam("vacuumId") Long id){
        Optional<Vacuum> optionalTeacher = vacuumService.findById(id);
        if(optionalTeacher.isPresent()) {
            return ResponseEntity.ok(optionalTeacher.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(/*consumes = MediaType.APPLICATION_JSON_VALUE,*/
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacuum createVacuum(/*@RequestBody Vacuum vacuum,*/ Authentication authentication){

        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_add_vacuum")) {
                String userEmail = authentication.getName();

                UserDetails loggedInUser = userService.loadUserByUsername(userEmail);

                User user = userService.loadUserByEmail(loggedInUser.getUsername());

                Vacuum vacuum1 = new Vacuum();
                vacuum1.setStatus(Status.OFF);
                vacuum1.setAddedBy(user);
                vacuum1.setActive(true);
                vacuum1.setVersion(0);
                vacuum1.setCount(0);

                return vacuumService.save(vacuum1);
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacuum updateVacuum(@RequestBody Vacuum vacuum){
        return vacuumService.save(vacuum);
    }

//    @PutMapping(value = "/start/{id}",
////            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> startVacuum(@PathVariable("id") Long id){
//        System.out.println("STARTING VACUUM : " + id);
//        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
//            String role = authority.getAuthority();
//            if(role.equals("can_start_vacuum")) {
//                Optional<Vacuum> vacuumOpt = vacuumService.findById(id);
//                Vacuum vacuum = vacuumOpt.get();
//
//                if (vacuum.getStatus() == Status.OFF) {
//                    vacuumService.start(id);
//                    return ResponseEntity.ok().build();
//                }
//            }
//        }
//        throw new AccessDeniedException("Forbidden");
//    }

    //ZA ASINHRONO IZVRSAVANJE
    @PutMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> startVacuum(@PathVariable("id") Long id) {
        System.out.println("STARTING VACUUM : " + id);

        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            String role = authority.getAuthority();
            if (role.equals("can_start_vacuum")) {
                Optional<Vacuum> vacuumOpt = vacuumService.findById(id);
                Vacuum vacuum = vacuumOpt.get();

                if (vacuum.getStatus() == Status.OFF) {
                    return CompletableFuture.supplyAsync(() -> {
                        vacuumService.start(id);
                        return ResponseEntity.ok().build();
                    });
                }
            }
        }

        throw new AccessDeniedException("Forbidden");
    }
    @PutMapping(value = "/stop/{id}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> stopVacuum(@PathVariable("id") Long id){
        System.out.println("STOPING VACUUM : "+ id);
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_stop_vacuum")) {
                Optional<Vacuum> vacuumOpt = vacuumService.findById(id);
                Vacuum vacuum = vacuumOpt.get();

                if (vacuum.getStatus() == Status.ON) {

                    return CompletableFuture.supplyAsync(() -> {
                        vacuumService.stop(id);
                        return ResponseEntity.ok().build();
                    });
                }
                }
            }
        throw new AccessDeniedException("Forbidden");
    }

    @PutMapping(value = "/discharge/{id}",
//            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>>dischargeVacuum(@PathVariable("id") Long id){
        System.out.println("DISCHARGING VACUUM : " + id);
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_discharge_vacuum")) {
                return CompletableFuture.supplyAsync(() -> {
                    vacuumService.discharging(id);
                    return ResponseEntity.ok().build();
                });
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @PutMapping(value = "remove/{id}")
    public ResponseEntity<?> deleteVacuum(@PathVariable("id") Long id, Authentication authentication){

        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_remove_vacuum")) {
                String userEmail = authentication.getName();

                UserDetails loggedInUser = userService.loadUserByUsername(userEmail);

                List<Vacuum> userVacuums = vacuumService.findVacuumsByAddedBy(loggedInUser.getUsername());

                Optional<Vacuum> vacuumOptional = vacuumService.findById(id);
                Vacuum vacuum = vacuumOptional.get();

                if (userVacuums.contains(vacuum) && vacuum.getStatus().equals(Status.OFF)){

                    Vacuum updatedVacuum = new Vacuum();
                    updatedVacuum.setVacuumId(vacuum.getVacuumId());
                    updatedVacuum.setStatus(vacuum.getStatus());
                    updatedVacuum.setAddedBy(vacuum.getAddedBy());
                    updatedVacuum.setActive(false);
                    updatedVacuum.setCount(vacuum.getCount());
                    updatedVacuum.setVersion(vacuum.getVersion());

                    vacuumService.save(updatedVacuum);

                    return ResponseEntity.ok().build();
                } else {
                    throw new AccessDeniedException("Forbidden");
                }
            }
        }
        throw new AccessDeniedException("Forbidden");
    }
}
