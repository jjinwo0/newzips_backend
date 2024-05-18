package com.ssafy.happyhouse.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MapMarkerInfo {

    private final String aptCode;
    private final String dealAmount;
    private final String exclusiveArea;
    private final String dealDate;
    private final String apartmentName;
    private final Double lng;
    private final Double lat;

}
