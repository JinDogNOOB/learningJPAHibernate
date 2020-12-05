package com.jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jpabook.jpashop.domain.Member;

import org.springframework.stereotype.Repository;
/**
 * @Repository 가 붙어있으면 App에 설정되어있는 컴포넌트 스캔에 의해 스프링 빈으로 자동 등록됨
 * 그리고 JPA 전용 예외가 발생하면 스프링이 추상화한 예외로 변환해준다.
 * ex) javax.persistence.NoResultException 을 org.springframework.dao.EmptyResultDataAccessException으로 변환해서 서비스 계층에 반환한다
 * 따라서 서비스는 JPA에 의존적인 예외처리를 하지 않아도 된다.
 */
@Repository
public class MemberRepository {
    /**
     * 순수 자바환경에서는 em팩토리에서 em를 직접 생성해서 사용했지만 
     * 스프링이나 J2EE 컨테이너를 사용하면 컨테이너가 엔티티매니저를 관리하고 제공해준다. 
     * @PersistenceContext는 엔티티매니저 주입
     * 만약에 팩토리를 얻고싶으면 @PersistenceUnit
     */
    @PersistenceContext
    EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
