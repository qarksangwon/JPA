package com.spb.total.repository;

import com.spb.total.entity.Cart;
import com.spb.total.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@Transactional // 데이터 베이스 논리적 작업 단위
@TestPropertySource(locations = "classpath:application-test2.properties")
class CartRepositoryTest {
    @Autowired // 스프링 컨테이너에서 해당 빈에 해당하는 의존성 주입 받는 어노테이션
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext // JPA의 Entity Manager를 사용한다는 의미(의존성 주입 받음)
    EntityManager em;

    // 회원 엔티티 생성
    public Member createMemberInfo(){
        Member member = new Member();
        member.setEmail("parksangwon@kh.com");
        member.setPwd("1234");
        member.setName("박상원");
//        member.setRegDate(LocalDateTime.now());
        return member;
    }

    @Test
    @DisplayName("장바구니와 회원 정보 매핑 테스트")
    public void findCartAndMemberTest(){
        Member member = createMemberInfo();
        memberRepository.save(member);
        Cart cart = new Cart();
        cart.setCartName("오늘의 쇼핑");
        cart.setMember(member);
        cartRepository.save(cart);
        em.flush(); // 영속성 컨텍스트에 데이터 저장 후  flush() 호출하여 데이터베이스에 반영
        em.clear(); // 영속성 켄텍스트를 비움

        Optional<Cart> saveCart = cartRepository.findById(cart.getId());
        if(saveCart.isPresent()){
            Cart testCart = saveCart.get();
            log.warn(testCart.getMember().getEmail());
        }

    }

}