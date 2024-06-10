package com.spb.total.controller;

import com.spb.total.dto.BoardDto;
import com.spb.total.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 게시글 등록
    @PostMapping("/new")
    public ResponseEntity<Boolean> boardRegister(@RequestBody BoardDto boardDto){
        boolean isTrue = boardService.saveBoard(boardDto);
        return ResponseEntity.ok(isTrue);
    }

    // 게시글 수정
    @PutMapping("/modify/{id}")
    public ResponseEntity<Boolean> boardModify(@PathVariable Long id, @RequestBody BoardDto boardDto){
        boolean isTrue = boardService.modifyBoard(id,boardDto);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> boardDelete(@PathVariable Long id) {
        boolean isTrue = boardService.deleteBoard(id);
        return ResponseEntity.ok(isTrue);
    }
    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> boardList() {
        List<BoardDto> list = boardService.getBoardList();
        return ResponseEntity.ok(list);
    }
    // 게시글 목록 페이징
    @GetMapping("/list/page")
    public ResponseEntity<List<BoardDto>> boardList(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        List<BoardDto> list = boardService.getBoardList(page, size);
        return ResponseEntity.ok(list);
    }
    // 게시글 상세 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<BoardDto> boardDetail(@PathVariable Long id) {
        BoardDto boardDto = boardService.getBoardDetail(id);
        return ResponseEntity.ok(boardDto);
    }
    // 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<List<BoardDto>> boardSearch(@RequestParam String keyword) {
        List<BoardDto> list = boardService.searchBoard(keyword);
        return ResponseEntity.ok(list);
    }
}
