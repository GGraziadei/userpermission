package it.goodgamegroup.up.services;

import it.goodgamegroup.up.dto.PermissionTypeDTO;
import it.goodgamegroup.up.entities.PermissionType;
import it.goodgamegroup.up.mappers.PermissionTypeMapper;
import it.goodgamegroup.up.repositories.PermissionTypeRepository;
import it.goodgamegroup.up.services.dao.PermissionTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionTypeDefaultService implements PermissionTypeDAO {

    @Autowired
    private PermissionTypeRepository permissionTypeRepository;

    @Autowired
    private PermissionTypeMapper permissionTypeMapper;

    @Override
    public Optional<PermissionType> get(Long id) {
        return this.permissionTypeRepository.findById(id);
    }

    @Override
    public List<PermissionType> getAll() {
        return this.permissionTypeRepository.findAll();
    }

    @Override
    public PermissionType put(PermissionType permissionType) {
        return null;
    }

    public PermissionType put(PermissionTypeDTO permissionTypeDTO) {
        PermissionType permissionType = new PermissionType();
        permissionTypeMapper.updatePermissionTypeFromDTO(permissionTypeDTO , permissionType);
        return  this.permissionTypeRepository.save(permissionType);
    }

    @Override
    public void update(PermissionType permissionType) {
    }

    @Override
    public PermissionType update(PermissionTypeDTO permissionTypeDTO) {
        PermissionType permissionType = new PermissionType();
        permissionTypeMapper.updatePermissionTypeFromDTO(permissionTypeDTO , permissionType);
        this.permissionTypeRepository.save(permissionType);
        return permissionType;
    }


    @Override
    public void delete(PermissionType permissionType) {
        this.permissionTypeRepository.delete(permissionType);
    }

    @Override
    public void deleteById(Long id) {
        this.permissionTypeRepository.deleteById(id);
    }
}
