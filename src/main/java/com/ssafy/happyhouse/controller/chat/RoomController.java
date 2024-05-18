package com.ssafy.happyhouse.controller.chat;

import com.ssafy.happyhouse.entity.chat.EnteredRoom;
import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.service.chat.EnteredRoomService;
import com.ssafy.happyhouse.service.chat.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final EnteredRoomService enteredRoomService;

    @GetMapping("/list")
    public ResponseEntity<List<Room>> findRoomList() {

        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/joined/{memberId}")
    public ResponseEntity<List<EnteredRoom>> joinList(@PathVariable("memberId") Long memberId){

        return ResponseEntity.ok(enteredRoomService.findByMemberId(memberId));
    }
}
