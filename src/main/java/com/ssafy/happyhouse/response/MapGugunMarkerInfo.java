package com.ssafy.happyhouse.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MapGugunMarkerInfo {
    private final String gugunName;
    private final String avgDealAmount;
    private final Double lng;
    private final Double lat;
}
