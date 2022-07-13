package it.goodgamegroup.up.services.task;

import it.goodgamegroup.up.configurations.ZoomConfiguration;
import it.goodgamegroup.up.dto.zoom.ZoomMeetingObjectDTO;
import it.goodgamegroup.up.dto.zoom.ZoomMeetingSettingsDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.events.NewZoomMeeting;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class CreateZoomMeeting {

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Job(name = "CREATE ZOOM MEETING  " , retries = 1)
    public void createZoomMeetingTask(String zoomUserId , ZoomMeetingSettingsDTO settingsDTO , User hostUser , List<User> userList){
        ZoomMeetingObjectDTO zoomMeetingObjectDTO = new ZoomMeetingObjectDTO();
        String apiUrl = "https://api.zoom.us/v2/users/" + zoomUserId + "/meetings";
        String password = UUID.randomUUID().toString();
        log.info("Meeting password " + password);
        zoomMeetingObjectDTO.setPassword(password);
        zoomMeetingObjectDTO.setHost_email(hostUser.getEmail());
        zoomMeetingObjectDTO.setSettings(settingsDTO);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + ZoomConfiguration.generateZoomJWTTOken());
        headers.add("content-type", "application/json");
        HttpEntity<ZoomMeetingObjectDTO> httpEntity = new HttpEntity<ZoomMeetingObjectDTO>(zoomMeetingObjectDTO, headers);
        ResponseEntity<ZoomMeetingObjectDTO> zEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, ZoomMeetingObjectDTO.class);
        if(zEntity.getStatusCodeValue() == 201) {
            log.debug("Zooom meeeting response {}",zEntity);
        } else {
            log.debug("Error while creating zoom meeting {}", zEntity.getStatusCode());
        }
        Map<String , Object > sources = new HashMap<>();
        sources.put(NewZoomMeeting.ZOOM_MEETING , zoomMeetingObjectDTO);
        sources.put(NewZoomMeeting.HOST_USER , hostUser);
        sources.put(NewZoomMeeting.USER_LIST , userList);
        NewZoomMeeting event = new NewZoomMeeting(sources );
        this.eventPublisher.publishEvent(event);
    }
}
