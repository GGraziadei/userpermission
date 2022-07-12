package it.goodgamegroup.up.dto.zoom;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZoomMeetingTrackingFieldsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    public String field;

    public String value;

    public Boolean visible;

}
