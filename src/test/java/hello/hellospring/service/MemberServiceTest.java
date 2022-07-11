package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    private MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository(); //새로운 객체가 생성되어서 다른 repository로 테스트를 하고 있는 상황.
    // 현재는 repository의 store가 static 선언이기 때문에 데이터 저장은 동일한 곳에서 이루어지기는 하나, 다른 객체기 때문에 테스트 시 이렇게 생성하는 것은 잘못된 것.
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    // 각 테스트 후에 repository를 비워주지 않으면 중복된 명칭으로 값이 들어갈 수 있기 때문에 test 후에 repo 비우는 작업을 넣어주어야 함.
    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring1");
        memberService.join(member);

        Member member2 = new Member();
        member2.setName("spring2");
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

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {

    }

}