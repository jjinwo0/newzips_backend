package com.ssafy.happyhouse.entity.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class HouseDeal {

    @NotNull
    private Long aptCode;

    private String dealAmount;

    private Integer buildYear;

    private String dealYear;

    private String dealMonth;

    private String dealDay;

    private String dealDate;

    private String roadName;

    private String dongCode;

    private String apartmentName;

    private Double exclusiveArea;

    private String jibun;

    private Integer floor;

}