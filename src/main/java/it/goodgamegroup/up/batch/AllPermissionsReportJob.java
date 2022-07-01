package it.goodgamegroup.up.batch;

import it.goodgamegroup.up.batch.readers.PermissionDefaultReader;
import it.goodgamegroup.up.batch.writers.PermissionCSVWriter;
import it.goodgamegroup.up.entities.reports.UserJoinPermission;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class AllPermissionsReportJob {

    @Value("${spring.batch.chunkSize}")
    private int chunkSize;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PermissionDefaultReader permissionDefaultReader;

    @Autowired
    private PermissionCSVWriter permissionCSVWriter;

    @Bean
    @Qualifier("allReport")
    public Job allReportJob(){
        return this.jobBuilderFactory.get("allReport" + System.currentTimeMillis())
                .start(allGenerateCsvFile())
                .build();
    }

    @Bean
    public Step allGenerateCsvFile() {
        return this.stepBuilderFactory.get("generateCsvFile" + System.currentTimeMillis())
                .<UserJoinPermission, UserJoinPermission>chunk(this.chunkSize)
                .reader(this.permissionDefaultReader)
                .writer(this.permissionCSVWriter)
                .build();
    }

}
