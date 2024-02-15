package rs.raf.demo.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.demo.model.*;
import rs.raf.demo.repositories.*;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final VacuumRepository vacuumRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, VacuumRepository vacuumRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vacuumRepository = vacuumRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Data...");

        User user1 = new User();
        user1.setFirstName("Dusan");
        user1.setLastName("Eric");
        user1.setEmail("dusaneric@gmail.com");
        user1.setPassword(this.passwordEncoder.encode("user1"));
        user1.setPermissions("can_read_users,can_search_vacuum,can_add_vacuum,can_discharge_vacuum,can_start_vacuum,can_stop_vacuum,can_remove_vacuum");
        this.userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("Milos");
        user2.setLastName("Milosevic");
        user2.setEmail("milosmilosevic@gmail.com");
        user2.setPassword(this.passwordEncoder.encode("user2"));
        user2.setPermissions("can_search_vacuum,can_add_vacuum,can_remove_vacuum");
        this.userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Stefan");
        user3.setLastName("Stefanovic");
        user3.setEmail("stefanstefanovic@gmail.com");
        user3.setPassword(this.passwordEncoder.encode("user3"));
        user3.setPermissions("can_search_vacuum,can_start_vacuum,can_discharge_vacuum,can_stop_vacuum");
        this.userRepository.save(user3);

        User user4 = new User();
        user4.setFirstName("Luka");
        user4.setLastName("Lukic");
        user4.setEmail("lukalukic@gmail.com");
        user4.setPassword(this.passwordEncoder.encode("user4"));
        user4.setPermissions("can_remove_vacuum");
        this.userRepository.save(user4);

        Vacuum vacuum1 = new Vacuum();
        vacuum1.setStatus(Status.OFF);
        vacuum1.setAddedBy(user1);
        vacuum1.setActive(true);
        vacuum1.setCount(2);
        this.vacuumRepository.save(vacuum1);

        Vacuum vacuum2 = new Vacuum();
        vacuum2.setStatus(Status.OFF);
        vacuum2.setAddedBy(user2);
        vacuum2.setActive(true);
        this.vacuumRepository.save(vacuum2);

        Vacuum vacuum3 = new Vacuum();
        vacuum3.setStatus(Status.ON);
        vacuum3.setAddedBy(user2);
        vacuum3.setActive(true);
        this.vacuumRepository.save(vacuum3);

        Vacuum vacuum4 = new Vacuum();
        vacuum4.setStatus(Status.ON);
        vacuum4.setAddedBy(user2);
        vacuum4.setActive(false);
        this.vacuumRepository.save(vacuum4);

        Vacuum vacuum5 = new Vacuum();
        vacuum5.setStatus(Status.OFF);
        vacuum5.setAddedBy(user1);
        vacuum5.setActive(true);
        this.vacuumRepository.save(vacuum5);

        Vacuum vacuum6 = new Vacuum();
        vacuum6.setStatus(Status.OFF);
        vacuum6.setAddedBy(user3);
        vacuum6.setActive(true);
        this.vacuumRepository.save(vacuum6);

        Vacuum vacuum7 = new Vacuum();
        vacuum7.setStatus(Status.DISCHARGING);
        vacuum7.setAddedBy(user3);
        vacuum7.setActive(true);
        this.vacuumRepository.save(vacuum7);

        Vacuum vacuum8 = new Vacuum();
        vacuum8.setStatus(Status.ON);
        vacuum8.setAddedBy(user1);
        vacuum8.setActive(false);
        this.vacuumRepository.save(vacuum8);

        Vacuum vacuum9 = new Vacuum();
        vacuum9.setStatus(Status.OFF);
        vacuum9.setAddedBy(user4);
        vacuum9.setActive(true);
        this.vacuumRepository.save(vacuum9);

        Vacuum vacuum10 = new Vacuum();
        vacuum10.setStatus(Status.ON);
        vacuum10.setAddedBy(user4);
        vacuum10.setActive(true);
        this.vacuumRepository.save(vacuum10);


        System.out.println("Data loaded!");
    }
}
