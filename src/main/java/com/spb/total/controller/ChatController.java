package com.spb.total.controller;

import com.spb.total.dto.ChatRoomReqDto;
import com.spb.total.dto.ChatRoomResDto;
import com.spb.total.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomReqDto){
        log.warn("ChatRoomDto (채팅방 정보) : {}",chatRoomReqDto);
        ChatRoomResDto room = chatService.createRoom(chatRoomReqDto.getName());
        System.out.println("방번호 : " + room.getRoomId());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<ChatRoomResDto> findAllRoom() {
        return chatService.findAllRoom();
    }

    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ChatRoomResDto findRoomById(@PathVariable String roomId) {
        return chatService.findRoomById(roomId);
    }
}
