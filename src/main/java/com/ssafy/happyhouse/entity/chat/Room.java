package com.ssafy.happyhouse.entity.chat;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Room {

    private Long id;

    private String name;

    @Builder
    public Room(String name) {
        this.name = name;
    }
}
