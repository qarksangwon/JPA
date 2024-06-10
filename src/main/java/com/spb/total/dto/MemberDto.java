package com.spb.total.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String pwd;
    private String name;
    private String email;
    private String image;
    private LocalDateTime regDate;
}
