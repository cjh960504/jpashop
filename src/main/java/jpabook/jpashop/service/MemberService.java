package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용 메서드에만 사용, 영속성 컨텍스트에 플러시하지않음
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /* 필드 주입 보다 생성자 주입 추천 */
    /* 생성자 하나만 존재 시, @Autowired 생략 가능 */
    /* final 키워드를 추가하면 컴파일 시점에 memberRepository 를 설정하지 않는 오류를 체크할 수 있다.*/
    /*  Lombok 의 @RequiredArgsConstructor 로 대체가능!
        public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    /**
     * 회원가입
     */
    @Transactional //readOnly = true 를 해제하고 해야함
    public Long join(Member member) {
        validateDuplicateMember(member); //예외 처리용
        memberRepository.save(member); //중복 회원이 존재하지 않을 때만 실행
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members
                = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
