package it.goodgamegroup.up.events;

import it.goodgamegroup.up.entities.UserAuthentication;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewAuthCreated extends ApplicationEvent {

    private final UserAuthentication userAuthentication;

    public NewAuthCreated(UserAuthentication source) {
        super(source);
        this.userAuthentication = source;
    }
}
