package it.goodgamegroup.up.batch.readers;

import it.goodgamegroup.up.entities.reports.UserJoinPermission;
import it.goodgamegroup.up.repositories.UserJoinPermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PermissionDefaultReader implements ItemReader<UserJoinPermission> {

    private List<UserJoinPermission> permissionList;

    @Autowired
    private UserJoinPermissionRepository userJoinPermissionRepository;

    @BeforeStep
    private void getPermissions(){
        this.permissionList = this.userJoinPermissionRepository.findAll();
    }

    @Override
    public UserJoinPermission read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (this.permissionList.size() > 0 ){
            UserJoinPermission userJoinPermission =  this.permissionList.remove(0);
            log.info("userJoinPermission {} " + userJoinPermission.toString());
            return  userJoinPermission;
        }
        return null;
    }

    @AfterStep
    private void freePermissions(){
        this.permissionList.clear();
    }
}
