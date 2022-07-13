package it.goodgamegroup.up.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.goodgamegroup.up.dto.zoom.ZoomMeetingSettingsDTO;
import it.goodgamegroup.up.entities.User;
import it.goodgamegroup.up.services.dao.UserDAO;
import it.goodgamegroup.up.services.task.CreateZoomMeeting;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meeting/zoom")
public class ZoomController {

    public static final String ZOOM_USER_ID = "zoomUserId";
    public static final String HOST_USER_ID = "hostUserId";
    public static final String LIST_USER_ID = "userList";
    public static final String ZOOM_SETTINGS = "zoomSettings";

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private UserDAO userService;

    @Autowired
    private CreateZoomMeeting createZoomMeeting;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public HttpStatus createNewMeeting(@RequestBody ObjectNode json) throws JsonProcessingException {
        String zoomUserId = json.get(ZOOM_USER_ID).asText();
        String hostUserId = json.get(HOST_USER_ID).asText();
        User hostUser = this.userService.get(UUID.fromString(hostUserId)).orElseThrow();
        // "userList" :  [ "1234" , "1234" , "123213" ]
        List<User> userList = new ArrayList<>();
        json.get(LIST_USER_ID).forEach( node -> {
            User user = this.userService.get(UUID.fromString(node.asText())).orElseThrow();
            userList.add(user);
        });
        ZoomMeetingSettingsDTO settings = objectMapper.treeToValue(json.get(ZOOM_SETTINGS) ,  ZoomMeetingSettingsDTO.class);
        this.jobScheduler.enqueue(() -> this.createZoomMeeting.createZoomMeetingTask(zoomUserId , settings,  hostUser , userList));
        return  HttpStatus.ACCEPTED;
    }
}
