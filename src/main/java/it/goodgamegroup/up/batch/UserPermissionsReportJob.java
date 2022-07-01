package it.goodgamegroup.up.batch;

import it.goodgamegroup.up.batch.readers.PermissionByUserReader;
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
public class UserPermissionsReportJob {

    @Value("${spring.batch.chunkSize}")
    private int chunkSize;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PermissionByUserReader permissionByUserReader;

    @Autowired
    private PermissionCSVWriter permissionCSVWriter;

    @Bean
    @Qualifier("userReport")
    public Job userReportJob(){
        return this.jobBuilderFactory.get("userReport" + System.currentTimeMillis())
                .start(userGenerateCsvFile())
                .build();
    }

    @Bean
    public Step userGenerateCsvFile() {
        return this.stepBuilderFactory.get("generateCsvFile" + System.currentTimeMillis())
                .<UserJoinPermission, UserJoinPermission>chunk(this.chunkSize)
                .reader(this.permissionByUserReader)
                .writer(this.permissionCSVWriter)
                .build();
    }

}
