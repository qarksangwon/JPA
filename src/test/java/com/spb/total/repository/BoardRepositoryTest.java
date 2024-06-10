package com.spb.total.repository;

import com.spb.total.entity.Board;
import com.spb.total.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("게시글 테스트 저장")
    public void createBoardTest(){
        Member member = new Member();
        member.setEmail("testEmail@test.com");
        memberRepository.save(member);

        for(int i = 1; i <= 5; i++){
            Board board = new Board();
            board.setTitle("게시글"+i);
            board.setContent("게시글 내용" + i);
            board.setImgPath("http://board"+i+".com");
            board.setRegDate(LocalDateTime.now());
            board.setMember(member);
            boardRepository.save(board);
        }

    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void findAllTest(){
        this.createBoardTest();
        Pageable pageable = PageRequest.of(1,2);
        // 사이즈 2개씩 페이징을 해서 몇 페이지를 가져올 지 PageRequest.of로 지정해서 받을 수 있다.
        Page<Board> boardList = boardRepository.findAll(pageable);
        for(Board b : boardList){
            log.warn(b.toString());
        }
    }

    @Test
    @DisplayName("특정 제목 게시글 조회 테스트")
    public void findByTitleTest(){
        this.createBoardTest();
        List<Board> boardList = boardRepository.findByTitleContaining("게시글4");
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

    @Test
    @DisplayName("Member email 로 작성 게시글 찾기")
    public void findByMemberEmailTest(){
        this.createBoardTest();
        List<Board> boardList = boardRepository.findByMemberEmail("testEmail@test.com");
        for(Board b : boardList){
            log.warn(b.getTitle());
        }
    }

}