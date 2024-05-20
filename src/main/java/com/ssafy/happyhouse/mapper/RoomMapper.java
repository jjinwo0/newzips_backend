package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.chat.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoomMapper {

    Map<String, Object> findById(Long roomId);

    List<Map<String, Object>> findByMemberId(Long memberId);

    List<Map<String, Object>> findByExpertId(Long expertId);

    void createRoom(Map<String, Object> map);

    void deleteRoom(Long roomId);
}
