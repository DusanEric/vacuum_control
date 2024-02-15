package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public Optional<User> findById(Long userID) {
        return userRepository.findById(userID);
    }

    public void deleteById(Long studentID) {
        userRepository.deleteById(studentID);
    }

    public <S extends User> S save(S user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("EMAIL: " + email);
        User myUser = this.userRepository.findUserByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }

        String[] permissionsArray = myUser.getPermissions().split(",");

        List<GrantedAuthority> authorities = Arrays.stream(permissionsArray)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(),myUser.getPassword(),authorities);
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        System.out.println("EMAIL: " + email);
        User myUser = this.userRepository.findUserByEmail(email);

        return myUser;
    }
}
