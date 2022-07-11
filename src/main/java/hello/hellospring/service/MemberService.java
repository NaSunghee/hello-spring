package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // 서비스 클래스의 메서드는 비즈니스 의존적으로 설계
    // repository는 crud 스럽게

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        // 요구사항: 동명이인 금지
        validateDuplicateName(member); //중복 회원 거증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateName(Member member) {
        /*if ( memberRepository.findByName(member.getName()).isPresent() ) {
            new Exception();
        }*/
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    //if 문 대신에 ifPresent 사용
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 아이디로 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
