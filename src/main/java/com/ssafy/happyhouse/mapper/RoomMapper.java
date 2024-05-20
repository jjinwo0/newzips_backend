package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.chat.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoomMapper {

    List<Map<String, Object>> findAll();

    Map<String, Object> findById(Long roomId);
}
