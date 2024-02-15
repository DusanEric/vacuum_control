package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.User;
import rs.raf.demo.services.UserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllStudents(){
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_read_users")) {
                return userService.findAll();
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@PathVariable Long id) {
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_update_users")) {
                return userService.findById(id).orElse(null);
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user){
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_create_users")) {
                return userService.save(user);
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateStudent(@RequestBody User updatedUser){
        System.out.println("updating");
        System.out.println(updatedUser.getUserId());
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
            String role = authority.getAuthority();
            if(role.equals("can_update_users")) {
                User existingUser = userService.findById(updatedUser.getUserId()).orElse(null);

                if (existingUser != null) {
                    updatedUser.setUserId(existingUser.getUserId());
                    updatedUser.setPassword(existingUser.getPassword());

                    return userService.save(updatedUser);
                }
            }
        }
        throw new AccessDeniedException("Forbidden");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Optional<User> optionalStudent = userService.findById(id);
        if(optionalStudent.isPresent()) {

            for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
                String role = authority.getAuthority();
                if(role.equals("can_delete_users")) {
                    userService.deleteById(id);
                    return ResponseEntity.ok().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

}
