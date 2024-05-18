package com.ssafy.happyhouse.service.chat;

import com.ssafy.happyhouse.entity.chat.EnteredRoom;
import com.ssafy.happyhouse.mapper.EnteredRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnteredRoomService {

    private final EnteredRoomMapper enteredRoomMapper;

    public List<EnteredRoom> findAll() {

        return enteredRoomMapper.findAll();
    }

    public List<EnteredRoom> findByMemberId(Long memberId) {

        return enteredRoomMapper.findByMemberId(memberId);
    }

    public List<EnteredRoom> findByRoomId(Long roomId) {

        return enteredRoomMapper.findByRoomId(roomId);
    }
}
