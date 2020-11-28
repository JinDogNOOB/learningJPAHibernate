package study;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    /**
     * 
     * 코드는 3부분으로 나뉨
     * 
     * 엔티티 매니저 설정
     * 트랜잭션 관리
     * 비즈니스 로직
     * 
     * 엔티티매니저 팩토리 생성할때 우리가 만들었던 META_INF내 persistence.xml 을 참고한다
     * 엔티티 매니저 팩토리를 생성하는 비용이 크니 보통 애플리케이션 전체에서 딱 한번만 생성하고 공유해서 써야한다.
     * 
     * 사용이 끝난 앤티티 매니저는 종료해줘야한다
     * 
     * 애플리케이션이 꺼졌을때 엔티니 매니저 팩토리도 종료가 되어야한다
     */
    public static void main( String[] args )
    {
        System.out.println(RoleType.ADMIN);
        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_unit");

        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction tx = em.getTransaction();

        try{
            tx.begin(); // 트랜잭션 시작
            logic(em); // 비즈니스 로직 시작
            tx.commit(); // 트랜잭션 커밋
        }catch(Exception e){
            tx.rollback(); // 트랜잭션 롤백
        }finally{
            em.close(); // 엔티티 매니저 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 종료

        System.out.println( "Done!!" );
        
    }

    private static void logic(EntityManager em){
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        // 등록 
        em.persist(member);

        // 수정
        member.setAge(20);

        // 한건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + " age=" + findMember.getAge());

        // 목록 조회
        /*
        검색쿼리는 그 엔티티를 전부 어플 메모리에 불러와서
        할수도없고 그래서 JPA는 JPQL이라는 추상화된 쿼리언어로 문제를 해결한다.
        JPQL은 엔티티 객체를 대상으로 쿼리한다
        아래에서 보듯이 from Member 는 테이블 MEMBER가 아니라 엔티티 Member다 
        JPQL은 데이터베이트 테이블 모른다 

        */
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        System.out.println("members size = " + members.size());

        em.remove(member);

        
    }
}
