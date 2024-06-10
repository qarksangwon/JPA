package com.spb.total.service;

import com.spb.total.dto.MemberDto;
import com.spb.total.entity.Member;
import com.spb.total.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 전체 조회
    public List<MemberDto> getMemberList(){
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member m : members){
            memberDtos.add(convertEntityToDto(m));
        }
        return memberDtos;
    }

    // 회원 상세 조회
    public MemberDto getMemberDetail(String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("해당 회원 없음"));
        return convertEntityToDto(member);
    }

    // 회원 가입
    public boolean createMember(MemberDto memberDto){
        boolean isExist = memberRepository.existsByEmail(memberDto.getEmail());
        if(isExist) return false;
        else{
            Member member = new Member();
            member.setPwd(memberDto.getPwd());
            member.setEmail(memberDto.getEmail());
            member.setName(memberDto.getName());
            member.setImage(memberDto.getImage());
            memberRepository.save(member);
            return true;
        }
    }

    // 회원 수정
    public boolean modifyMember(MemberDto memberDto){
        try{
            Member member = memberRepository.findByEmail(memberDto.getEmail())
                    .orElseThrow(()->new RuntimeException("해당 회원 없음"));
            member.setName(memberDto.getName());
            member.setImage(memberDto.getImage());
            memberRepository.save(member);
            return true;
        }catch (Exception e){
            log.error("Error occurred during modifyMember : {}", e.getMessage(), e);
            return false;
        }
    }

    // 회원 삭제
    public boolean deleteMember(String email){
        try{
            Member member = memberRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("해당 회원 없음"));
            memberRepository.delete(member);
            return true;
        }catch (Exception e){
            log.error("Error occurred during deleteMember : {}", e.getMessage(), e);
            return false;
        }
    }

    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setPwd(member.getPwd());
        memberDto.setImage(member.getImage());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}
