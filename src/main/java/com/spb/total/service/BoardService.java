package com.spb.total.service;

import com.spb.total.dto.BoardDto;
import com.spb.total.entity.Board;
import com.spb.total.entity.Category;
import com.spb.total.entity.Location;
import com.spb.total.entity.Member;
import com.spb.total.repository.BoardRepository;
import com.spb.total.repository.CategoryRepository;
import com.spb.total.repository.LocationRepository;
import com.spb.total.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;
    private final LocationRepository locationRepository;

    // 게시글 등록
    @Transactional
    public boolean saveBoard(BoardDto boardDto){
        try{
            Board board = createBoardFromDto(boardDto);
            Board savedBoard = boardRepository.save(board);
            Location location = createLocationFromDto(boardDto, savedBoard);
            locationRepository.save(location);
            return true;
        }catch (Exception e){
            log.error("Error occurred during saveBoard: {}", e.getMessage(), e);
            return false;
        }
    }

    // 게시글 전체 조회
    public List<BoardDto> getBoardList(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board b : boards){
            boardDtos.add(convertEntityToDto(b));
        }
        return boardDtos;
    }

    // 게시글 상세 조회
    public BoardDto getBoardDetail(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new RuntimeException("게시글이 존재하지 않음")
        );
        return convertEntityToDto(board);
    }

    // 게시글 페이징
    public List<BoardDto> getBoardList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board b : boards){
            boardDtos.add(convertEntityToDto(b));
        }
        return boardDtos;
    }

    // 게시글 수정
    public boolean modifyBoard(Long id, BoardDto boardDto){
        try{
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("게시글이 존재하지 않음.")
            );
            Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("회원이 존재하지 않음.")
            );
            Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("카테고리가 존재하지 않음.")
            );
            board.setTitle(boardDto.getTitle());
            board.setCategory(category);
            board.setContent(boardDto.getContent());
            board.setImgPath(boardDto.getImg());
            board.setMember(member);
            boardRepository.save(board);
            return true;
        }catch (Exception e){
            log.info("Error occurred during modifyBoard: {}", e.getMessage(), e);
            return false;
        }
    }

    // 게시글 삭제
    public boolean deleteBoard(Long id){
        try{
            Board board = boardRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("게시글이 존재하지 않음.")
            );
            boardRepository.delete(board);
            return true;
        }catch (Exception e){
            log.info("Error occurred during deleteBoard: {}", e.getMessage(), e);
            return false;
        }
    }

    // 게시글 검색 (제목)
    public List<BoardDto> searchBoard(String keyword){
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board b : boards){
            boardDtos.add(convertEntityToDto(b));
        }
        return boardDtos;
    }

    // 게시글 엔티티를 DTO로 변환
    private BoardDto convertEntityToDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setCategoryId(board.getCategory().getCategoryId());
        boardDto.setContent(board.getContent());
        boardDto.setImg(board.getImgPath());
        boardDto.setEmail(board.getMember().getEmail());
        boardDto.setRegDate(board.getRegDate());

        Location location = board.getLocation(); // Board 엔티티에 Location 엔티티에 대한 참조가 필요
        if (location != null) { // Location 정보가 있는 경우에만 설정
            boardDto.setAddress(location.getAddress());
            boardDto.setLatitude(location.getLatitude());
            boardDto.setLongitude(location.getLongitude());
        }
        return boardDto;
    }


    // BoardDto 를 board entity 로 변환
    private Board createBoardFromDto(BoardDto boardDto) {
        Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                () -> new RuntimeException("회원이 존재하지 않음.")
        );

        Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                () -> new RuntimeException("카테고리가 존재하지 않음.")
        );

        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setImgPath(boardDto.getImg());
        board.setMember(member);
        board.setCategory(category);
        return board;
    }

    // BoardDto 와 Board entity 의 정보로 Location 생성
    private Location createLocationFromDto(BoardDto boardDto, Board board) {
        Location location = new Location();
        location.setBoard(board);
        location.setAddress(boardDto.getAddress());
        location.setLatitude(boardDto.getLatitude());
        location.setLongitude(boardDto.getLongitude());
        return location;
    }

}
