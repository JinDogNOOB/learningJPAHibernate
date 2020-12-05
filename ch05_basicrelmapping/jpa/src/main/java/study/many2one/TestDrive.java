package study.many2one;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class TestDrive {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_unit");
    public void testSave(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // 팀1 저장
        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("팀1");
        em.persist(team1);

        // 회원 1 저장
        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("멤버1");
        member1.setTeam(team1);
        em.persist(member1);

        // 회원 2 저장
        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("멤버2");
        member2.setTeam(team1);
        em.persist(member2);

        // JPA 엔티티를 저장할때 연관된 모든 엔티티는 영속상태여야한다

        // 연관관계 있는 엔티티 조회하는 방법은 두가지
        /*
            객체 그래프 탐색(객체 연관관계를 통해)
            객체지향 쿼리 사용(JPQL)
        */

        // 객체지향쿼리
        System.out.println(member2.getTeam().getId());
        System.out.println(member2.getTeam().getName());

        // JPQL 쿼리
       /*  
        String jpql = "awlwxr m deo mMwvwe m join m.team t where" + "t.name:teamName";
        List<Member> resultList = em.createQuery(jpql, Member.class)
            .setParameter("teamName", "팀1")
            .getResultList();
        
        for(Member member : resultList){
            System.out.println(member.getUsername()); 
        */


        // 팀 수정
        // 일단 팀 만들고 영속으로 돌리고
        Team team2 = new Team();
        team2.setId("team2");
        team2.setName("팀2");
        em.persist(team2);

        // 수정
        Member member2beUpdated = em.find(Member.class, "member1");
        member2beUpdated.setTeam(team2);

        // 연관관계 제거
        member2beUpdated.setTeam(null); 

        // 양방향으로 넣어줬음
        team1.getMembers();


        /*  
            team1.getMembers().add(member3);
            // 연관관계의 주인이 아니므로 무시

            member3.setTeam(team1);
            // 연관관계의 주인이므로 동작함
        */


        // 연관된 엔티티를 삭제하려면 
        // 기존에 있던 연관관계를 먼저 제거하고 삭제해야함
        // 그렇지 않으면 외래키 제약조건으로 인해, 데이터베이스에서 오류가 발생함
        // 팀1에는 회원1,2 가 소속되어있음, 이때 팀1을 삭제하려면 연관관계를 먼저 끊어야함
        member1.setTeam(null);
        member2.setTeam(null);
        em.remove(team1);


        tx.commit();
        em.close();
    }
}
