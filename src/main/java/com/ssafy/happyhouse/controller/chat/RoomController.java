package com.ssafy.happyhouse.controller.chat;

import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.service.chat.EnteredRoomService;
import com.ssafy.happyhouse.service.chat.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    private final EnteredRoomService enteredRoomService;

    @GetMapping("/list")
    public ResponseEntity<List<Map<String, Object>>> findRoomList() {

        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/joined/{memberId}")
    public ResponseEntity<List<Map<String, Object>>> joinList(@PathVariable("memberId") Long memberId){

        return ResponseEntity.ok(enteredRoomService.findByMemberId(memberId));
    }

    @PostMapping("/init/{roomId}/{memberId}")
    public ResponseEntity<?> initChatRoom(@PathVariable("roomId") Long roomId,
                                          @PathVariable("memberId") Long memberId){

        enteredRoomService.createChatRoom(roomId, memberId);

        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/quit/{enteredRoomId}")
    public ResponseEntity<?> quitChatRoom(@PathVariable("enteredRoomId") Long enteredRoomId){

        enteredRoomService.deleteById(enteredRoomId);

        return ResponseEntity.ok(true);
    }
}
