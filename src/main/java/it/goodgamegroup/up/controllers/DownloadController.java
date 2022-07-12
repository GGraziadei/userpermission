package it.goodgamegroup.up.controllers;

import it.goodgamegroup.up.configurations.DownloadConfiguration;
import it.goodgamegroup.up.configurations.DownloadExtension;
import it.goodgamegroup.up.configurations.DownloadSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
@Slf4j
@RequestMapping("/download")
public class DownloadController {

    @RequestMapping("/report/{fileName:.+}")
    public void downloadReport(HttpServletRequest request, HttpServletResponse response, @PathVariable("fileName") String fileName) throws Exception {
        ClassPathResource resource = new ClassPathResource(DownloadConfiguration.getFileSource(DownloadSource.REPORT)
                + fileName
                + DownloadExtension.CSV.getExtension());

        if (resource.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(resource.getFilename());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            if(DownloadConfiguration.inlineDownload ){
                response.setHeader("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"");
            }else{
                response.setHeader("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
            }
            response.setContentLength((int) resource.contentLength());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(resource.getFile()));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }else{
            log.info("file " + resource.getFilename() + " not found");
        }
    }
}
