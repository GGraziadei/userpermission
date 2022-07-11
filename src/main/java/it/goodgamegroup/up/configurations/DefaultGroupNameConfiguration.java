package it.goodgamegroup.up.configurations;

import it.goodgamegroup.up.entities.UserAuthenticationGroup;
import it.goodgamegroup.up.services.dao.UserAuthenticationGroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultGroupNameConfiguration {

    @Autowired
    private UserAuthenticationGroupDAO userAuthenticationGroupService;

    @Bean
    public UserAuthenticationGroup adminGroup(){
        UserAuthenticationGroup userAuthenticationGroup =
                this.userAuthenticationGroupService.getByName(DefaultGroupName.ADMIN)!=null
                    ?this.userAuthenticationGroupService.getByName(DefaultGroupName.ADMIN):new UserAuthenticationGroup();
        userAuthenticationGroup.setCode(DefaultGroupName.ADMIN);
        userAuthenticationGroup.setName(DefaultGroupName.ADMIN);
        return this.userAuthenticationGroupService.put(userAuthenticationGroup);
    }

    @Bean
    public UserAuthenticationGroup userGroup(){
        UserAuthenticationGroup userAuthenticationGroup =
                this.userAuthenticationGroupService.getByName(DefaultGroupName.USER)!=null
                        ?this.userAuthenticationGroupService.getByName(DefaultGroupName.USER):new UserAuthenticationGroup();
        userAuthenticationGroup.setCode(DefaultGroupName.USER);
        userAuthenticationGroup.setName(DefaultGroupName.USER);
        return this.userAuthenticationGroupService.put(userAuthenticationGroup);
    }
}
