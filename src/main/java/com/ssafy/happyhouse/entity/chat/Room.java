package com.ssafy.happyhouse.entity.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class Room {

    private Long id;

    private String name;

    private Integer price;

    private List<EnteredRoom> enteredRooms = new ArrayList<>();

    @Builder
    public Room(String name) {
        this.name = name;
    }
}
