package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.entity.chat.EnteredRoom;
import com.ssafy.happyhouse.entity.chat.Status;
import com.ssafy.happyhouse.mapper.EnteredRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnteredRoomService {

    private final EnteredRoomMapper enteredRoomMapper;

    public List<EnteredRoom> findAll() {

        return enteredRoomMapper.findAll();
    }

    public List<Map<String, Object>> findByMemberId(Long memberId) {

        log.info("memberId :: {}", memberId);

        List<Map<String, Object>> list = enteredRoomMapper.findByMemberId(memberId);

        return list;
    }

    public List<EnteredRoom> findByRoomId(Long roomId) {

        return enteredRoomMapper.findByRoomId(roomId);
    }

    public void createChatRoom(Long roomId, Long memberId) {

        Map<String, Object> input = new HashMap<>();

        input.put("roomId", roomId);
        input.put("memberId", memberId);
        input.put("status", Status.LIVE.getStatus());

        enteredRoomMapper.createChatRoom(input);
    }

    public void deleteById(Long id){

        enteredRoomMapper.deleteById(id);
    }
}
