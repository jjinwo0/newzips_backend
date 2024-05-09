package com.ssafy.happyhouse.entity.house;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class House {

    @NotNull
    private Long id;

    @NotNull
    private String apartmentName;

    @NotNull
    private String address;

    @NotNull
    private String zipcode;

    @Builder
    public House(String apartmentName, String address, String zipcode) {
        this.apartmentName = apartmentName;
        this.address = address;
        this.zipcode = zipcode;
    }
}


