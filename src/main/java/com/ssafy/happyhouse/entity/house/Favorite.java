package com.ssafy.happyhouse.entity.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @NotNull
    private Long id;

    private Long member_id;

    private Long house_id;
}


