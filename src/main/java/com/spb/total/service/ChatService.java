package com.spb.total.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spb.total.dto.ChatMessageDto;
import com.spb.total.dto.ChatRoomResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper; // JSON 문자열로 변환할 때 사용하는 객체
    private Map<String, ChatRoomResDto> chatRooms; // 채팅방들을 담을 맵

    @PostConstruct // 의존성 주입 이후 초기화를 수행하는 메소드
    private void init(){
        //채팅방 담을 맵을 초기화
        chatRooms = new LinkedHashMap<>(); // 채팅방을 담을 맵
    }

    public List<ChatRoomResDto> findAllRoom() {
        // 현재 채팅방 전체 리스트 반환
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomResDto findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    // 방 개설
    public ChatRoomResDto createRoom(String name){
        String randomId = UUID.randomUUID().toString(); // 고유 채팅방을 만들기 위해 채팅방 ID 랜덤 생성
        log.warn("UUID : " +randomId);
        ChatRoomResDto chatRoom = ChatRoomResDto.builder()
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .build();
        chatRooms.put(randomId,chatRoom); // 해당 채팅방 번호와 그에 맞게 생성한 채팅방 정보를 값으로 넣음
        return chatRoom;
    }

    public void removeRoom(String roomId){
        ChatRoomResDto room = chatRooms.get(roomId);
        if(room != null) { // 채팅 방에 대한 정보를 가져오는 room 을 가져오고
            if(room.isSessionEmpty()){ // 해당 채팅 방에 연결된 세션이 있는지 확인한 뒤
                chatRooms.remove(roomId); // 방 정보 전체 관리하는 현재 Map에서 삭제
            }
        }
    }

    // 채팅방에서 퇴장한 세션을 제거하는 메소드
    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMessageDto chatMessage){
        ChatRoomResDto room = findRoomById(roomId); // 채팅방 정보를 가져와서
        if(room != null){
            room.getSessions().remove(session); // 퇴장할 세션을 방에 연결된 세션에서 삭제
            if(chatMessage.getSender() != null) {
                // 세션을 삭제시킴 = 사용자가 퇴장 =>  해당 사용자 이름이 null 이 아니라면
                // 퇴장했다는 메세지를 보낸다.
                chatMessage.setMessage(chatMessage.getSender() + "님 퇴장");
                sendMessageToAll(roomId, chatMessage);
            }
            log.debug("Session removed : " + session);
            if(room.isSessionEmpty()){
                // 퇴장해서 방에 연결된 세션을 확인해 비어있다면 방도 삭제
                removeRoom(roomId);
            }
        }
    }
    
    public  void sendMessageToAll(String roomId, ChatMessageDto message){
        ChatRoomResDto room = findRoomById(roomId); // 방 정보를 가져와서
        if(room != null){
            for(WebSocketSession session : room.getSessions()){
                //해당 방에 연결된 모든 세션에 for 문을 통해 메세지 broadCast
                sendMessage(session, message);
            }
        }
    }
    public <T> void sendMessage(WebSocketSession session, T message){
        try{
            // 해당 메세지를 세션에 전송
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
    }

}
