package rs.raf.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.services.ErrorMessageService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/errors")
public class ErrorMessageRestController {

    private final ErrorMessageService errorMessageService;

    public ErrorMessageRestController(ErrorMessageService errorMessageService) {
        this.errorMessageService = errorMessageService;
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErrorMessage> getAllStudents(@PathVariable Long id){
        return errorMessageService.findMessagesByVacuumID(id);
    }

//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ErrorMessage getUserById(@PathVariable Long id) {
//        return errorMessageService.findById(id).orElse(null);
//    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage createUser(@RequestBody ErrorMessage user){
        return errorMessageService.save(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ErrorMessage updateStudent(@RequestBody ErrorMessage updatedUser){
        return errorMessageService.save(updatedUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        Optional<ErrorMessage> optionalStudent = errorMessageService.findById(id);
        if(optionalStudent.isPresent()) {
            errorMessageService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
