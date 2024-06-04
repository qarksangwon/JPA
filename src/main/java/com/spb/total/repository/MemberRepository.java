package com.spb.total.repository;

import com.spb.total.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //Optional 의 기능은 findByEmail 으로 부른 값이 null 일 때
    //프로그램이 죽는걸 방지해서 없다고 알려주기 위한 반환타입
    Optional<Member> findByEmail(String email);  // 개별 회원 정보 조회
    List<Member> findAll(); // 전체 회원 조회
    Optional<Member> findByEmailAndPwd(String email, String pwd); // 로그인
}

// JUnit 테스트 목록
// 1. 전체 회원 조회
// 2. 이메일을 통한 개별 회원 조회
// 3. 가입 여부 확인 ( Email 로 확인 ) -> 2번이랑 같은 로직
// 4. 로그인 체크 (이메일과 비밀번호)
