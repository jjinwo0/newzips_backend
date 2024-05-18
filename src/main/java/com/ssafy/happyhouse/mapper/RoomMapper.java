package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.chat.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {

    List<Room> findAll();
}
