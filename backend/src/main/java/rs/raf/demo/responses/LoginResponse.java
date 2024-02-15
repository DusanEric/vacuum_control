package rs.raf.demo.responses;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class LoginResponse {
    private String jwt;
    private Collection<? extends GrantedAuthority> authorities;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public LoginResponse(String jwt, Collection<? extends GrantedAuthority> authorities) {
        this.jwt = jwt;
        this.authorities = authorities;
    }
}
