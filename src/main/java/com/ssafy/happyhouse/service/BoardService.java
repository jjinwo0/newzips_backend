package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.board.Board;
import com.ssafy.happyhouse.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<Board> findAll() {

        return boardMapper.findAll();
    }

    public Board findById(Long id) {

        return boardMapper.findById(id);
    }

    @Transactional
    public void writeBoard(BoardDto.Write dto){

        Map<String, String> map = new HashMap<>();

        map.put("title", dto.getTitle());
        map.put("content", dto.getContent());
        map.put("author", dto.getAuthor());

        boardMapper.writeBoard(map);
    }

    @Transactional
    public void updateBoard(BoardDto.Update dto, Long id){

        Map<String, Object> map = new HashMap<>();

        map.put("title", dto.getTitle());
        map.put("content", dto.getContent());
        map.put("id", id);

        boardMapper.updateBoard(map);
    }

    @Transactional
    public void deleteBoard(Long id) {

        boardMapper.deleteBoard(id);
    }
}
