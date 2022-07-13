package it.goodgamegroup.up.events;

import it.goodgamegroup.up.dto.zoom.ZoomMeetingObjectDTO;
import it.goodgamegroup.up.entities.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.util.List;
import java.util.Map;

public class NewZoomMeeting extends ApplicationEvent {

    public static final String USER_LIST = "userList";
    public static final String ZOOM_MEETING = "zoomMeeting";
    public static final String HOST_USER = "hostUser";

    private Map<String , Object> sources;
    private User hostUser;
    private List<User> userList;
    private ZoomMeetingObjectDTO zoomMeeting;

    public NewZoomMeeting(Map<String , Object> sources) {
        super(sources);
        this.hostUser = (User) sources.get(HOST_USER);
        this.zoomMeeting = (ZoomMeetingObjectDTO) sources.get(ZOOM_MEETING);
        this.userList = (List<User>) sources.get(USER_LIST);
    }

    public User getHostUser() {
        return hostUser;
    }

    public List<User> getUserList() {
        return userList;
    }

    public ZoomMeetingObjectDTO getZoomMeeting() {
        return zoomMeeting;
    }
}
