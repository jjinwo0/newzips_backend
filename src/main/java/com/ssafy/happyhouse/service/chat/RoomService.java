package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;

    public Map<String, Object> findById(Long roomId) {

        return roomMapper.findById(roomId);
    }

    public List<Map<String, Object>> findByMemberId(Long memberId) {

        return roomMapper.findByMemberId(memberId);
    }

    public List<Map<String, Object>> findByExpertId(Long expertId) {

        return roomMapper.findByExpertId(expertId);
    }

    @Transactional
    public void createRoom(Long expertId, Long memberId) {

        Map<String, Object> map = new HashMap<>();

        map.put("status", "LIVE");
        map.put("expertId", expertId);
        map.put("memberId", memberId);

        roomMapper.createRoom(map);
    }

    @Transactional
    public void deleteRoom(Long roomId) {

        roomMapper.deleteRoom(roomId);
    }
}
