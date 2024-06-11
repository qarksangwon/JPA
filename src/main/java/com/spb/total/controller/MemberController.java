package com.spb.total.controller;

import com.spb.total.dto.MemberDto;
import com.spb.total.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원 가입
//    @PutMapping("/signup")
//    public ResponseEntity<Boolean> memberCreate(@RequestBody MemberDto memberDto){
//        boolean isTrue = memberService.createMember(memberDto);
//        return ResponseEntity.ok(isTrue);
//    }

    //회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList(){
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }

    // 회원 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberDto memberDto){
        boolean isTrue = memberService.modifyMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }

    //회원 삭제
    @GetMapping("/delete/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email){
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }


}
