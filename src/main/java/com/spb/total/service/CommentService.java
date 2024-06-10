package com.spb.total.service;

import com.spb.total.dto.CommentDto;
import com.spb.total.entity.Board;
import com.spb.total.entity.Comment;
import com.spb.total.entity.Member;
import com.spb.total.repository.BoardRepository;
import com.spb.total.repository.CommentRepository;
import com.spb.total.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 댓글 등록
    public boolean commentRegister(CommentDto commentDto){
        try{
            Comment comment = new Comment();
            Board board = boardRepository.findById(commentDto.getBoardId()).orElseThrow(
                    () -> new RuntimeException("게시글이 존재하지 않음.")
            );
            Member member = memberRepository.findByEmail(commentDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("회원이 존재하지 않음.")
            );
            comment.setContent(commentDto.getContent());
            comment.setMember(member);
            comment.setBoard(board);
            commentRepository.save(comment);
            return true;
        }catch (Exception e){
            log.info("Error occurred during commentRegister: {}", e.getMessage(), e);
            return false;
        }
    }

    // 댓글 수정
    public boolean commentModify(CommentDto commentDto){
        try {
            Comment comment = commentRepository.findById(commentDto.getCommentId()).orElseThrow(
                    () -> new RuntimeException("댓글이 존재하지 않음.")
            );
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during commentModify: {}", e.getMessage(), e);
            return false;
        }
    }

    // 댓글 삭제
    public boolean commentDelete(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new RuntimeException("댓글이 존재하지 않음.")
            );
            commentRepository.delete(comment);
            return true;
        } catch (Exception e) {
            log.info("Error occurred during commentDelete: {}", e.getMessage(), e);
            return false;
        }
    }

    // 댓글 목록 조회
    public List<CommentDto> getCommentList(Long boardId) {
        try {
            Board board = boardRepository.findById(boardId).orElseThrow(
                    () -> new RuntimeException("게시글이 존재하지 않음.")
            );
            List<Comment> comments = commentRepository.findByBoard(board);
            List<CommentDto> commentDtos = new ArrayList<>();
            for(Comment comment : comments) {
                commentDtos.add(convertEntityToDto(comment));
            }
            return commentDtos;
        } catch (Exception e) {
            log.info("Error occurred during getCommentList: {}", e.getMessage(), e);
            return null;
        }
    }

    // 댓글 검색
    public List<CommentDto> getCommentList(String keyword) {
        List<Comment> comments = commentRepository.findByContentContaining(keyword);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(convertEntityToDto(comment));
        }
        return commentDtos;
    }

    // 댓글 엔티티를 댓글 DTO로 변환
    private CommentDto convertEntityToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setBoardId(comment.getBoard().getBoardId());
        commentDto.setEmail(comment.getMember().getEmail());
        commentDto.setContent(comment.getContent());
        commentDto.setRegDate(comment.getRegDate());
        return commentDto;
    }
}
