package com.jpabook.jpashop.service;


import java.util.List;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// javax보다 springgramwok의 Transactional 이 더 많은 기능 제공해줌
/**
 * @Transactional: 스프링 프레임워크는 이 어노테이션이 붙어 있는 클래스나 메소드에 트랜잭션을 적용한다
 * 외부에서 이 클래스의 메소드를 호출할 때 트랜잭션을 시작하고 메소드를 종료할 때 트랜잭션을 커밋한다
 * 만약 예외가 발생하면 트랜잭션을 롤백한다
 * @Transactional은 RuntimeException과 그 자식들인 어너체크(Unchecked) 예외만 롤백한다.
 * 만약 체크 예외가 발생해도 롤백하고 싶으면 @Transactional(rollbackFor = Exception.class) 처럼 롤백할 예외를 지정해야한다
 */
@Service
@org.springframework.transaction.annotation.Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }


    /**
     * 중복 체크
     * @param member
     * 이렇게 검증로직이 있어도 멀티 스레드 상황을 고려해서 회원 테이블의 회원명 컬럼에 유니크 제약 조건을 추가하는 것이 안전함
     */
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    
}
