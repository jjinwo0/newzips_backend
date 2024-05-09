package com.ssafy.happyhouse.entity.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
public class HouseDeal {

    @NotNull
    private Long aptCode;

    private Long dealAmount;

    private Integer buildYear;

    private String dealDate;

    private String roadName;

    private String dongCode;

    private String apartmentName;

    private Double exclusiveArea;

    private String jibun;

    private Integer floor;

}

