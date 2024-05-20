package com.ssafy.happyhouse.entity.chat;

import com.ssafy.happyhouse.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class EnteredRoom {

    private Long id;

    private Member member;

    private Room room;

    private Status status;

    private LocalDateTime entiredTime;

    @Builder
    public EnteredRoom(Member member, Room room) {
        this.member = member;
        this.room = room;
    }
}
