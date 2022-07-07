package it.goodgamegroup.up.services.task;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.repositories.UserAuthenticationRepository;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AddUser {

    @Autowired
    private UserDAO userService;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Job(name = "addUser task - fiscal code %1" , retries = 1)
    public void addUserTask(UserDTO userDTO , String fiscalCode){
        User user = this.userService.put(userDTO);
        //Add authentication for USER
        jobScheduler.enqueue(() -> addUserAuthTask(user.getId()));
    }

    @Job(name = "addUserAuth task - userId %0" , retries = 1)
    public void addUserAuthTask( UUID userId ){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = this.userService.get(userId).orElseThrow();
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserName(user.getEmail());
        userAuthentication.setPassword(passwordEncoder.encode(user.getFiscalCode()));
        userAuthentication.setActive(true);
        userAuthentication.setRoles(Constant.USER);
        userAuthentication.setUser(user);
        this.userAuthenticationRepository.save(userAuthentication);
    }

}
