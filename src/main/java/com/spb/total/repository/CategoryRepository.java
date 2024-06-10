package com.spb.total.repository;

import com.spb.total.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 사용자 Email 로 Category 찾기
    List<Category>findByMemberEmail(String email);
    
}
