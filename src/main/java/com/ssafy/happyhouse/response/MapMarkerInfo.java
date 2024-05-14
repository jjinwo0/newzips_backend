package com.ssafy.happyhouse.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MapMarkerInfo {

    private String aptCode;
    private String dealAmount;
    private String exclusiveArea;
    private String dealDate;
    private String apartmentName;
    private Double lng;
    private Double lat;

}
