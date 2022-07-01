package it.goodgamegroup.up.services;

import it.goodgamegroup.up.configurations.Constant;
import it.goodgamegroup.up.dto.UserDTO;
import it.goodgamegroup.up.entities.UserAuthentication;
import it.goodgamegroup.up.mappers.UserMapper;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.repositories.UserAuthenticationRepository;
import it.goodgamegroup.up.repositories.UserRepository;
import it.goodgamegroup.up.services.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDefaultService implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public Optional<User> get(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User put(User user) {
        return null;
    }


    public User put(UserDTO userDTO) {
        BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();
        //1. create usere in db
        User user = new User();
        userMapper.updateUserFromDTO(userDTO, user);
        this.userRepository.save(user);

        //2. Create UserAuth
        UserAuthentication userAuthentication  = this.userAuthenticationRepository.save(UserAuthentication.builder()
                        .userName(userDTO.getEmail())
                        .password(passwordEncoder.encode(userDTO.getFiscalCode()))
                        .tsCreate(Instant.now()).tsUpdate(Instant.now())
                        .active(true).roles(Constant.USER).user(user)
                .build());

        //3. Link UserAuthentication to user
        user.setUserAuthentication(userAuthentication);
        return this.userRepository.save(user);
    }

    @Override
    public void update(User user) {
        //
    }

    @Override
    public User update(UserDTO userDTO) {
        User user = this.userRepository.getById(userDTO.getId());
        userMapper.updateUserFromDTO(userDTO, user);
        return this.userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        this.userRepository.delete(user);
    }

    @Override
    public void deleteById(UUID id) {
        this.userRepository.deleteById(id);
    }

}
