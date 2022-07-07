package it.goodgamegroup.up.mappers;


import it.goodgamegroup.up.dto.PermissionTypeDTO;
import it.goodgamegroup.up.entities.PermissionType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PermissionTypeMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionTypeFromDTO (PermissionTypeDTO dto, @MappingTarget PermissionType permissionType);
}
