

@Entity(name="Member")
public class Member{
    @Column(name="name")
    private String uesrName;
}

// #### JPQL 사용
// 테이블 이름 기준이 아니라 엔티티 이름기준으로
/*
String jpql = "select m from Member as m where m.username = 'kim'";
List<Member> resultList = em.createQuery(jpql, Member.class).getResultList();
*/


// #### Criteria 쿼리 사용
/*
// 사용준비
CriteriaBuilder cb = em.getCriteriaBuilder();
CriteriaQuery<Member> query = cb.createQuery(Member.class);

// 루트 클래스(조회를 시작할 클래스)
Root<Member> m = query.from(Member.class);

// 쿼리 생성
CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("userName"), "kim"));
List<Member> resultList = em.createQuery(cq).getResultList();

일련의 너무나 복잡하고 가독성이 떨어진다
*/


// #### QueryDSL
/*
// 준비
JPAQuery query = new JPAQuery(em);
QMember qMember = QMember.member;

// 쿼리, 결과 조회
List<Member> members = query.from(qMember).where(qMember.userName.eq("kim")).list(qMember);

// 단순하고 가독성 있다
*/


// #### Native SQL
/*
String sql = "SELECT ID,AGE,TEAM_ID, NAME FROM MEMBER WHERE NAME = 'kim'";
List<Member> resultList = em.createNativeQuery(sql, Member.class).getResultList();

*/


// #### JDBC 직접사용 or MyBatis같은 매퍼 프레임워크 사용
/*
일단 JPA구현체가 JDBC커넥션을 획득하는 메소드를 제공하면
Session session = entitiyManager.unwrap(Session.class);
session.doWork(new Work(){
    @Override
    public void execute(Connection connection) throws SQLException{
        //work.....
    }
})

영속성컨텍스트에 값 바꿔놓고 플러시 안한상태에서 
위와같이 직접 jdbc사용해서 쿼리하면 불일치가 발생해서
무결성이 훼손된다
그래서 적절한 시기에 영속성컨텍스트 플러시 해줘야함

스프링 프레임워크는 손쉽게 통합 가능
*/