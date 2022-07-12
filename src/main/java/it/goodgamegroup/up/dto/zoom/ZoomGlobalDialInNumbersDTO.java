package it.goodgamegroup.up.dto.zoom;

import lombok.Data;

import java.io.Serializable;

@Data
public class ZoomGlobalDialInNumbersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String country;

    private String country_name;

    private String city;

    private String number;

    private String type;


}
