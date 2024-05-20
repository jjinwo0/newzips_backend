package com.ssafy.happyhouse.controller.chat;

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

    @GetMapping("/joined/{memberId}")
    public ResponseEntity<List<Map<String, Object>>> joinList(@PathVariable("memberId") Long memberId){

        return ResponseEntity.ok(roomService.findByMemberId(memberId));
    }

    @GetMapping("/joined/expert/{expertId}")
    public ResponseEntity<List<Map<String, Object>>> joinListByExpertId(@PathVariable("expertId") Long expertId){

        return ResponseEntity.ok(roomService.findByExpertId(expertId));
    }

    @PostMapping("/init/{expertId}/{memberId}")
    public ResponseEntity<?> initChatRoom(@PathVariable("expertId") Long expertId,
                                          @PathVariable("memberId") Long memberId){

        roomService.createRoom(expertId, memberId);

        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/quit/{roomId}")
    public ResponseEntity<?> quitChatRoom(@PathVariable("roomId") Long roomId){

        roomService.deleteRoom(roomId);

        return ResponseEntity.ok(true);
    }
}
