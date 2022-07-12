package it.goodgamegroup.up.mappers;

import it.goodgamegroup.up.dto.UserAuthenticationGroupDTO;
import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserAuthenticationGroupMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserGroupFromDTO (UserAuthenticationGroupDTO dto, @MappingTarget UserAuthenticationGroup userAuthenticationGroup);

}
