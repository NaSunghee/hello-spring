package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring100");
        memberService.join(member);

        Member member2 = new Member();
        member2.setName("spring200");
        memberService.join(member2);

    }

    @Test
    void join() {
        //테스트에서 지키면 좋은 형식 given~ when~ then~ 문법
        //테스트의 주의사항 :: 예외처리

        //given: 주어진 데이터
        Member member = new Member();
        member.setName("hello");

        //when: 주어진 상황
        Long saveId = memberService.join(member);

        //then: 예측 결과 검증
        Member findmember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findmember.getName());
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        // assertThrows : 위 두번째 파라미터의 로직대로 진행했을 때 첫번째 파라미터 예외가 나와야 함.

/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            //예외가 발생 = 정상
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        //then
    }


}