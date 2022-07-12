package it.goodgamegroup.up.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloadConfiguration {
    public static final boolean inlineDownload = true ;

    public static String getFileSource(DownloadSource downloadSource ) {
        switch (downloadSource) {
            case REPORT: return "reports/";
        }
        return null;
    }
}
