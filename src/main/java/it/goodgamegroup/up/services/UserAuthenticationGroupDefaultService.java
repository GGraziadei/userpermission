package it.goodgamegroup.up.services;

import it.goodgamegroup.up.dto.UserAuthenticationGroupDTO;
import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import it.goodgamegroup.up.mappers.UserAuthenticationGroupMapper;
import it.goodgamegroup.up.repositories.UserAuthenticationGroupRepository;
import it.goodgamegroup.up.services.dao.UserAuthenticationGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationGroupDefaultService implements UserAuthenticationGroupDAO {
    @Autowired
    private UserAuthenticationGroupRepository userAuthenticationGroupRepository;

    @Autowired
    private UserAuthenticationGroupMapper userAuthenticationGroupMapper;

    @Override
    public Optional<UserAuthenticationGroup> get(Long id) {
        return this.userAuthenticationGroupRepository.findById(id);
    }

    @Override
    public List<UserAuthenticationGroup> getAll() {
        return this.userAuthenticationGroupRepository.findAll();
    }

    @Override
    public UserAuthenticationGroup put(UserAuthenticationGroup userAuthenticationGroup) {
        return this.userAuthenticationGroupRepository.save(userAuthenticationGroup);
    }

    @Override
    public UserAuthenticationGroup put(UserAuthenticationGroupDTO dto) {
        UserAuthenticationGroup userAuthenticationGroup = new UserAuthenticationGroup();
        userAuthenticationGroupMapper.updateUserGroupFromDTO(dto , userAuthenticationGroup);
        return this.userAuthenticationGroupRepository.save(userAuthenticationGroup);
    }

    @Override
    public void update(UserAuthenticationGroup userAuthenticationGroup) {
        this.userAuthenticationGroupRepository.save(userAuthenticationGroup);
    }

    @Override
    public void delete(UserAuthenticationGroup userAuthenticationGroup) {
        this.userAuthenticationGroupRepository.delete(userAuthenticationGroup);
    }

    @Override
    public void deleteById(Long id) {
        this.userAuthenticationGroupRepository.deleteById(id);
    }

    @Override
    public UserAuthenticationGroup getByName(String name) {
        return this.userAuthenticationGroupRepository.findByName(name);
    }
}
