package com.spb.total.repository;

import com.spb.total.entity.Category;
import com.spb.total.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("카테고리 생성 테스트")
    public void createCategory(){
        Member member = new Member();
        member.setEmail("test@test.com");
        memberRepository.save(member);
        for(int i = 1; i <= 3; i++){
            Category category = new Category();
            category.setCategoryName("카테고리"+i);
            category.setMember(member);
            categoryRepository.save(category);
        }
    }

    @Test
    @DisplayName("회원 Email로 카테고리 출력")
    public void findByMemberEmailTest(){
        this.createCategory();
        List<Category> categoryList = categoryRepository.findByMemberEmail("test@test.com");
        for(Category c : categoryList){
            log.warn(c.getCategoryName());
        }
    }
}