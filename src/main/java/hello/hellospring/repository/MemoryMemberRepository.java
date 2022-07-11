package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMemberRepository implements MemberRepository {

    // TODO:: 실무에서의 HashMap이 공유되는 리소스로 사용될 경우 동시성문제가 있을 수 있으므로 ConcurrentHashMap 사용 권장
    private static Map<Long, Member> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong();
    // 키 값을 생성. 실무에서는 동시성문제등을 고려하여 AtomicLong 사용

    @Override
    public Member save(Member member) {
        member.setId(sequence.incrementAndGet());
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //null 의 값일 경우도 감싸서 optional 객체로 리턴 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // filter 에서 걸러진 부분이 있으면 반환 없으면 Optional(null)
    }

    @Override
    public List<Member> findAll() {
        // store.values() 는 store에 담긴 값들
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
