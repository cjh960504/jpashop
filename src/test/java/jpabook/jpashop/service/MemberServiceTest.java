package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) // 스프링과 테스트 통합
@SpringBootTest //스프링 부트를 띄우고 테스트
@Transactional //반복 가능한 테스트 지원, 트랜잭션 시작 - 종료 후 강제 롤백
public class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입_성공() throws Exception {
        //Given
        Member member = new Member();
        member.setName("Choi");
        
        //When
        Long saveId = memberService.join(member);

        //Then
        Assertions.assertEquals(saveId, member.getId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //Given
        Member member1 = new Member();
        member1.setName("Choi");

        Member member2 = new Member();
        member2.setName("Choi");
        
        //When
        memberService.join(member1);
        memberService.join(member2);

        //Then
        fail("중복 회원명으로 인한 예외 발생해야 한다.");
    }

}