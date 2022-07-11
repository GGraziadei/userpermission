package it.goodgamegroup.up.services;

import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.repositories.UserAuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import it.goodgamegroup.up.security.UserDetailsImpl;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuthentication> userAuthentication = this.userAuthenticationRepository.findByUserName(username);
        userAuthentication.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        UserDetailsImpl userDetails = userAuthentication.map(UserDetailsImpl::new).get(); //Custom UserDetails
        log.info("Auth with user: " + userDetails.toString());
        return userDetails;
    }


}
