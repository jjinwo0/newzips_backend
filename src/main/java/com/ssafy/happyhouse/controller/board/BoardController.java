package com.ssafy.happyhouse.controller.board;

import com.ssafy.happyhouse.dto.board.BoardDto;
import com.ssafy.happyhouse.entity.board.Board;
import com.ssafy.happyhouse.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> getBoardList(){

        List<Board> boardList = boardService.findAll();

        return ResponseEntity.ok(boardList);
    }

        @GetMapping("/list/{id}")
    public ResponseEntity<?> getBoard(@PathVariable("id") Long id) {

        Board findBoard = boardService.findById(id);

        return ResponseEntity.ok(findBoard);
    }

    @PostMapping("/write")
    public ResponseEntity<?> writeBoard(@RequestBody BoardDto.Write dto){

        boardService.writeBoard(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateBoard(@RequestBody BoardDto.Update dto,
                                         @PathVariable("id") Long id){

        boardService.updateBoard(dto, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {

        boardService.deleteBoard(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
