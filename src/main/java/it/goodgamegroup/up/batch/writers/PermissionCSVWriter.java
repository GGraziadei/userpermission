package it.goodgamegroup.up.batch.writers;

import it.goodgamegroup.up.batch.ReportKey;
import it.goodgamegroup.up.entities.reports.UserJoinPermission;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;

@Component
public class PermissionCSVWriter extends FlatFileItemWriter<UserJoinPermission> {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        String jobName = stepExecution.getJobParameters().getString(ReportKey.JOB_NAME);
        setResource(new FileSystemResource("src/main/resources/reports/"+ jobName +".csv"));
    }

    @Autowired
    public PermissionCSVWriter() {


        setLineAggregator(new DelimitedLineAggregator<UserJoinPermission>() {
            {
                setDelimiter(";");
                setFieldExtractor(new BeanWrapperFieldExtractor<UserJoinPermission>() {
                    {
                        setNames(new String[] {
                                "permissionId" ,
                                "userId",
                                "firstName",
                                "lastName",
                                "fiscalCode",
                                "email",
                                "phoneNumber",
                                "tsStart",
                                "tsEnd"
                        });
                    }
                });
            }
        });

        setHeaderCallback(new FlatFileHeaderCallback() {

            public void writeHeader(Writer writer) throws IOException {
                writer.write("PERMISSION_ID;USER_ID;FIRST_NAME;LAST_NAME;FISCAL_CODE;EMAIL;PHONE_NUMBER;TS_START;TS_END");

            }
        });

    }
}
