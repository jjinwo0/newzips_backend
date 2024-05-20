package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.chat.EnteredRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EnteredRoomMapper {

    List<EnteredRoom> findAll();

    List<Map<String, Object>> findByMemberId(Long memberId);

    List<EnteredRoom> findByRoomId(Long roomId);

    void createChatRoom(Map<String, Object> input);

    void deleteById(Long enteredRoomId);
}
