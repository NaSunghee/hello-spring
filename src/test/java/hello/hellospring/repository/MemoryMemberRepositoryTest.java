package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

// 테스트는 순서에 독립적으로 실행되므로 어떤 게 뭐가 먼저 실행될 지 모름.
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
        //테스트 종료시마다 메모리 데이터를 삭제해주어야 테스트 순서에 상관없이 데이터 검증이 가능.ㅗ
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
//        System.out.println("result = " + (result == member));
//        Assertions.assertEquals(member, result);

        assertThat(member).isEqualTo(result); // static import 를 통해서 Assertions 객체 호출 없이 바로 메소드 호출 가능.
        //TODO:: 실무에서는 build tool 과 엮어서 오류 해결이 나지 않으면 다음단계로 넘어가지 못하도록 막는 기능 있음.
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("member1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        repository.save(member2);

        Member result = repository.findByName("member1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("member1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}