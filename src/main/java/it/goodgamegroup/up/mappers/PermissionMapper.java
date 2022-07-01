package it.goodgamegroup.up.mappers;

import it.goodgamegroup.up.dto.PermissionDTO;
import it.goodgamegroup.up.entities.Permission;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePermissionFromDTO (PermissionDTO dto, @MappingTarget Permission permission);

}
