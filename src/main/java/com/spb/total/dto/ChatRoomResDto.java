package com.spb.total.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class ChatRoomResDto {
    private String roomId; // 채팅방 ID
    private String name; // 채팅방 이름
    private LocalDateTime regDate; // 채팅방 생성 시간

    // 어노테이션으로 WebSocketSession 직렬화 방지.
    @JsonIgnore // session 아이디를 직렬화하면 안되는 경우가 있다고 한다.
    private Set<WebSocketSession> sessions; // 채팅방에 입장한 session 정보를 담을 Set

    // 세션 수 0 인지 확인하는 메소드
    public boolean isSessionEmpty() {
        return this.sessions.size() == 0;
    }

    @Builder
    public ChatRoomResDto(String roomId, String name, LocalDateTime regDate){
        this.roomId = roomId;
        this.name = name;
        this.regDate = regDate;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>()); // 동시성 접근 문제 해결을 위해 사용
    }
}
