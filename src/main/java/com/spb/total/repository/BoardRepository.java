package com.spb.total.repository;

import com.spb.total.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitleContaining(String keyword);
    List<Board> findByContentContaining(String keyword);
    List<Board> findAll();
}

// JUnit 테스트 목록
// 1. 키워드가 게시글의 제목에 포함된 글 
// 2. 키워드가 게시글의 내용에 포함된 글
// 3. 게시물 전체 출력