package com.spb.total.repository;

import com.spb.total.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 테스트 저장")
    public void createBoardTest(){
        for(int i = 1; i <= 5; i++){
            Board board = new Board();
            board.setTitle("게시글"+i);
            board.setContent("게시글 내용" + i);
            board.setImgPath("http://board"+i+".com");
            board.setRegDate(LocalDateTime.now());
            boardRepository.save(board);
        }
    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void findAllTest(){
        this.createBoardTest();
        List<Board> boardList = boardRepository.findAll();
        for(Board b : boardList){
            log.warn(b.toString());
        }
    }

    @Test
    @DisplayName("특정 제목 게시글 조회 테스트")
    public void findByTitleTest(){
        this.createBoardTest();
        Optional<Board> boardList = boardRepository.findByTitleContaining("게시글4");
//        Board board = boardRepository
//                .findByTitleContaining("게시글7")
//                .orElseThrow(()-> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        log.warn(boardList.toString());
    }

    @Test
    @DisplayName("특정 내용 게시글 조회 테스트")
    public void findByContentTest(){
        this.createBoardTest();
        List<Board> boardList = boardRepository.findByContentContaining("내용3");
        log.warn(boardList.toString());
    }

}