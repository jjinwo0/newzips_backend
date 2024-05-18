package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.entity.chat.Room;
import com.ssafy.happyhouse.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomService {

    private final RoomMapper roomMapper;

    public List<Room> findAll() {

        return roomMapper.findAll();
    }
}
