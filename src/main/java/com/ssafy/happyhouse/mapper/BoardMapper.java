package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.entity.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    void writeBoard(Map<String, String> map);

    List<Board> findAll();

    Board findById(Long id);

    void updateBoard(Map<String, Object> map);

    void deleteBoard(Long id);
}
