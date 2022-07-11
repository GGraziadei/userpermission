package it.goodgamegroup.up.security;

import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@ToString
public class UserDetailsImpl implements UserDetails {

    private final UserAuthentication userAuthentication;
    private final List<UserAuthenticationGroup> userAuthenticationGroups;

    public UserDetailsImpl(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
        this.userAuthenticationGroups = new ArrayList<>(userAuthentication.getUserAuthenticationGroups());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userAuthenticationGroups.stream()
                .map(g -> g.getCode().toUpperCase(Locale.ROOT))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
