package com.spb.total.service;

import com.spb.total.dto.CategoryDto;
import com.spb.total.entity.Category;
import com.spb.total.entity.Member;
import com.spb.total.repository.CategoryRepository;
import com.spb.total.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    // 카테고리 등록
    public boolean saveCategory(CategoryDto categoryDto){
        try{
            Category category = new Category();
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    ()-> new RuntimeException("회원 없음")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        }catch (Exception e){
            log.error("Error occurred during saveCategory : {}", e.getMessage(),e);
            return false;
        }
    }

    // 카테고리 수정
    public boolean modifyCategory(Long id, CategoryDto categoryDto){
        try{
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("카테고리가 존재하지 않음.")
            );
            Member member = memberRepository.findByEmail(categoryDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("회원이 존재하지 않음.")
            );
            category.setCategoryName(categoryDto.getCategoryName());
            category.setCategoryId(category.getCategoryId());
            category.setMember(member);
            categoryRepository.save(category);
            return true;
        }catch(Exception e){
            log.error("Error occurred during modifyCategory : {}", e.getMessage(),e);
            return false;
        }
    }

    //카테고리 삭제
    public boolean deleteCategory(Long id){
        try{
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("카테고리가 존재하지 않음.")
            );
            categoryRepository.delete(category);
            return true;
        }catch (Exception e){
            log.info("Error occurred during deleteCategory: {}", e.getMessage(), e);
            return false;
        }
    }

    // 카테고리 목록 조회
    public List<CategoryDto> getCategoryList() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for(Category category : categories) {
            categoryDtos.add(convertEntityToDto(category));
        }
        return categoryDtos;
    }

    // 카테고리 엔티티를 카테고리 DTO로 변환
    private CategoryDto convertEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setEmail(category.getMember().getEmail());
        return categoryDto;
    }
}
