package it.goodgamegroup.up.services.task;

import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.events.NewAuthCreated;
import it.goodgamegroup.up.mappers.UserMapper;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddUser {

    @Autowired
    private UserDAO userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Variante dismessa al fine di gestire gli errori di controllo sorgente
    @Job(name = "ADD USER - fiscal_code %1 " , retries = 1)
    public void addUserTask(UserDTO userDTO , String fiscalCode){
        User user = new User();
        userMapper.updateUserFromDTO(userDTO, user);
        user.setUserAuthentication(new UserAuthentication(user));
        this.userService.put(user);
        NewAuthCreated event = new NewAuthCreated(user.getUserAuthentication());
        this.eventPublisher.publishEvent(event);
    }

}
