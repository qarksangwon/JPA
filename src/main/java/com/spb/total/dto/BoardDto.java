package com.spb.total.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDto {
    private Long boardId;
    private String email;
    private Long categoryId;
    private String title;
    private String content;
    private String img;
    private LocalDateTime regDate;
    private String address;
    private Double latitude;
    private Double longitude;
}
