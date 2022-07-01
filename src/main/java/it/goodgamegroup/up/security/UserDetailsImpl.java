package it.goodgamegroup.up.security;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.entities.UserAuthentication;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class UserDetailsImpl implements UserDetails {

    private final UserAuthentication userAuthentication;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
        this.authorities =  Arrays.stream(userAuthentication.getRoles().split(Constant.ROLE_SEPARATOR))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userAuthentication.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userAuthentication.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userAuthentication.getActive();
    }
}
