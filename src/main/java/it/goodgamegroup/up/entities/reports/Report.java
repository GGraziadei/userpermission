package it.goodgamegroup.up.entities.reports;

import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.events.NewReportGenerated;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Report {
    private User user;
    private String fileName;
    private UUID jobName;

    @Autowired
    @ToString.Exclude
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    private void createReportEvent(){
        this.eventPublisher.publishEvent(new NewReportGenerated(this));
    }
}
