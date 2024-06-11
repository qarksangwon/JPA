package com.spb.total.dto;

import com.spb.total.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String name;
    private String email;
    private String image;
    private LocalDateTime regDate;

    public static MemberResponseDto of(Member member){
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .image(member.getImage())
                .regDate(member.getRegDate())
                .build();
    }
}
