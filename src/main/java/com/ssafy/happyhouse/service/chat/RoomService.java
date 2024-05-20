package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;

    public List<Map<String, Object>> findAll() {

        return roomMapper.findAll();
    }

    public Map<String, Object> findById(Long roomId) {

        return roomMapper.findById(roomId);
    }
}
