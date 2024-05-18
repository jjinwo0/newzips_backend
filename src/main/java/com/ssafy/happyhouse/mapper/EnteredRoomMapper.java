package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.chat.EnteredRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnteredRoomMapper {

    List<EnteredRoom> findAll();

    List<EnteredRoom> findByMemberId(Long memberId);

    List<EnteredRoom> findByRoomId(Long roomId);
}
