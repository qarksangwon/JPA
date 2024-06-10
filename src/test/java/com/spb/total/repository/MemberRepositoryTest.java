package com.spb.total.repository;

import com.spb.total.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("테스트 회원 데이터 저장")
    public void createMemberTest(){
        for(int i = 1; i <= 5; i ++){
            Member member = new Member();
            member.setEmail("test"+i+"@test.com");
            member.setPwd("pwd"+i);
            member.setName("test"+i);
            member.setImage("http://test"+i);
//            member.setRegDate(LocalDateTime.now());
            memberRepository.save(member);
        }
    }

    @Test
    @DisplayName("이메일로 회원 정보 조회 테스트")
    public void findByEmailTest(){
        this.createMemberTest();
        Optional<Member> member = memberRepository.findByEmail("test2@test.com");
        log.warn(member.toString());
    }

    @Test
    @DisplayName("전체 회원 조회 테스트")
    public void findAllTest(){
        this.createMemberTest();
        List<Member> memberList = memberRepository.findAll();
        for(Member m : memberList){
            log.warn("가입된 회원 이름 : " +m.getName());
        }
    }

    @Test
    @DisplayName("로그인 체크 테스트")
    public void findByEmailAndPwdTest(){
        this.createMemberTest();
        Optional<Member> logIn1 = memberRepository.findByEmailAndPwd("test2@test.com", "pwd1");
        Optional<Member> logIn2 = memberRepository.findByEmailAndPwd("test2@test.com", "pwd2");
        log.warn("로그인 체크 1 : " + logIn1.isEmpty());
        log.warn("로그인 체크 2 : " + logIn2.isEmpty());
    }

}