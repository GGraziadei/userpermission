package it.goodgamegroup.up.events;

import it.goodgamegroup.up.entities.reports.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class NewReportGenerated extends ApplicationEvent {
    private final Report report;

    public NewReportGenerated( Report report) {
        super(report);
        this.report = report;
        log.info("New event generated {}" + this.toString());
    }
}
