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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public User put(User user) {
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public User put(UserDTO userDTO) {
        User user = new User();
        userMapper.updateUserFromDTO(userDTO, user);
        return this.userRepository.save(user);
    }

    @Override
    public void update(User user) {
        this.userRepository.save(user);
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
