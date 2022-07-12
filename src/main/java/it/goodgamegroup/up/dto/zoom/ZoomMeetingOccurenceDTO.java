package it.goodgamegroup.up.dto.zoom;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZoomMeetingOccurenceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String occurrence_id;

    private String start_time;

    private Integer duration;

    private String status;

}
