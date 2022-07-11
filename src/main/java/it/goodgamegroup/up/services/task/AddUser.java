package it.goodgamegroup.up.services.task;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.mappers.UserMapper;
import it.goodgamegroup.up.repositories.UserAuthenticationRepository;
import it.goodgamegroup.up.repositories.UserRepository;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddUser {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Job(name = "ADD USER - fiscal_code %1 " , retries = 1)
    public void addUserTask(UserDTO userDTO , String fiscalCode){
        User user = new User();
        UserAuthentication userAuthentication = new UserAuthentication();
        userMapper.updateUserFromDTO(userDTO, user);
        userAuthentication.setUserName(user.getEmail());
        userAuthentication.setPassword(passwordEncoder.encode(user.getFiscalCode()));
        userAuthentication.setActive(true);
        userAuthentication.setRoles(Constant.USER);
        userAuthentication.setUser(user);
        user.setUserAuthentication(userAuthentication);
        this.userRepository.save(user);
    }

}
