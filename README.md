# learn hibernate
* 교재 : 자바 ORM 표준 JPA 프로그래밍 - 김영한
* n+1 문제 : 연관된 엔티티또 또 불러와서 일을 두번한다. 
    - (A)OneToMany(B) 를 필드로 가지고 있는 엔티티A를 조회했을때 
        - for(a : A) a.getB() 사용했을때
        - 그때그때 또 B를 가져온다(글로벌 패치 전략이 LAZY 라서?)
        - A조회된 갯수만큼 B를조회하는 쿼리를 날림
    - 해결법
        - join fetch -> 조인해서 A, B한번에 가져온다 #inner join 사용한다
        - @EntityGraph -> 해당 쿼리만 패치전략 Eager # left outer join 사용
        - 공통적으로 카티션 곱이 생기므로 해결해야함 -> 일대다 필드를 set으로 하거나 select 문에 distinct 추가
        - 부가적인건 jpa n+1 cartesian 검색 
## ch01_testing > ./ch01_testing
* 기본적인 hibernate 사용
    - 엔티티매니저팩토리 생성은 비용이 많이 드니 어플리케이션 당 하나
    - 앤티티매니저를 여러 쓰레드에서 공동으로 쓰지말라
    - persistence.xml 작성!!
## ch03 > ./ch01_testing
* 영속성 관리
* em.persist(member)
* 단순히 회원 엔티티를 저장이 아니라 -> 엔티티 매니저를 사용해서 회원 엔티티를 영속성 컨텍스트에 저장
* 영속성 컨텍스트는 엔티티 매니저를 생성할때 하나 만들어지고, 엔티티매니저를 통해서 이것에 접근하거나 관리할 수 있음
* 엔티티 생명주기
    - 비영속(new/transient) : 영속성 컨텍스트와 전혀 관계가 없음
        - new -> persist() -> managed
    - 영속(managed) : 영속성 컨텍스트에 저장된 상태
        - managed -> close(), clear(), detach() -> detached
        - managed -> remove() -> removed
        - DB에 flush 명령
        - find() with JPQL -> managed 
    - 준영속(detached) : 영속성 컨텍스트에 저장되었다가 분리된 상태
        - detached -> merge() -> managed
    - 삭제(removed) : 삭제된 상태
        - removed -> persist() -> managed

* 엔티티 생명주기 설명
    - 비영속
        - 엔티티 객체를 new Member() 식으로 생성한 상태, 순수한 객체 상태, 따라서 영속성컨텍스트나 디비와 관계없음
    - 영속
        - 엔티티 매니저를 통해서 persist() 메소드 통해 영속성 컨텍스트에 저장됐다. 이렇게 영속성 컨텍스트가 관리하는 엔티티를 영속상태라함
        - 엔티티 매니저에 의해 관리되는 상태가 되었다...
        - em.find() 또는 JPQL을 통해 조회한 엔티티도 영속성 컨텍스트에 의해 관리된다
    - 준영속
        - 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 됨
        - em.detach() 를 통해서 준영속 상태로 만들 수 있다. 
        - em.close() 를 호출해서 영속성 컨텍스트를 닫거나 em.clear() 를 호출해서 영속성 컨텍스트를 초기화해도 관리되던 영속상태 엔티티들이 동일하게 준영속상태로 된다
    - 삭제
        - 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다
        - em.remove()
* 영속성 컨텍스트 특징
    - 식별자값
        - 영속성 컨텍스트는 엔티티를 식별자값(@Id로 테이블의 기본키와 매핑한 값)으로 구분함
        - 반드시 영속상태는 식별자 값이 있어야함 -> 없으면 exception
    - 영속성 컨텍스트에 있는 엔티티의 저장 시기
        - 보통 트랜잭션을 커밋하는 순간 -> 영속성컨텍스트에 저장된 엔티티를 데이터베이스에 반영
        - 위 같은 상황을 flush 라고 함(쓰기 지연 SQL 저장소 에 모인 쿼리(변경감지:스냅샷 비교 후 확인된 변경사항적용 쿼리 포함)를 db에 보내서 서로 상태 동기화)

## ch04 > ./ch01_testing
* 엔티티 매핑

## ch05 > ./ch05_basicremapping
* 연관관계 매핑 기초
    - 객체의 참조와 테이블의 외래키를 매핑
* 방향
    - 방향
        - 둘 중 한쪽만 참조하는 것을 단방향 관계라고 함
        - 회원 -> 팀 또는 팀 -> 회원
        - 서로 참조하는 경우는 양방향이라고함
    - 다중성
        - N:1, 1:N, 1:1, N:M
        - 만약에 회원과 팀이 관계가 있을때 
        - 여러 회원은 한 팀에 속하므로 회원과 팀은 다대 일 관계임
    - 연관관계의 주인
        - 객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 한다

## ch06 > ./ch06_admapping
* 고급 매핑
* 상속관계 매핑
    - 객체의 상속 관계를 테이터베이스에 매핑하는 방법
    - 환경
        - orm(상속) < - > db(슈퍼타입 서브타입) 과 유사 이런식을 할때 사용함
    - DB 테이블 설계 방법
        - 1. 각각의 테이블로 변환 (공통 테이블, 앨범 테이블, 무비 테이블, 책 테이블) 조인전략
        - 2. 통합 테이블로 변환 (하나의 테이블에 컬럼 다 집어넣고 구분컬럼 DTYPE 삽입)
        - 3. 서브타입 테이블로 변환 (서브타입마다 하나의 테이블 생성 (앨범, 무비, 책 테이블))
    - 조인전략 > joinStrategy
        - 장점 : 테이블정규화, 저장공간 효율적
        - 단점 : 조회할 때 조인이 많이 사용되므로 성능 저하우려, 조회쿼리 복잡, 등록할때 insert sql 두번 동작
    - 단일 테이블 전략 > singleTableStrategy
        - 조인전략이랑 클래스설계는 다른게 없음
        - 어노테이션 밸류만 single_table로 바꾸면됨
        - db 컬럼에 null허용 해줘야한다
        - 장점 : 조인이 필요없으므로 조회성능 빠름, 조회쿼리 단순
        - 단점 : 자식 엔티티가 매핑한 컬럼은 모두 null허용해야함, 단일 테이블에 전부 저장하므로 테이블이 커질 수 있음, 상황에 따라서 성능저하 유발
        - 주의 : 구분컬럼 꼭 넣어야한다. 구분컬럼 설정안하면 기본적으로 엔티티 이름 사용함
    - 구현 클래스마다 테이블 전략
        - @Inheritance 의 strategy에 TABLE_PER_CLASS 설정
        - 클래스 구조는 조인전략과 다를 게 없음
        - 장점 : 서브타입 구분해서 처리할때 효과적, not null 제약조건 사용가능
        - 단점 : 여러 자식 테이블을 함께 조회할때 성능 느림(sql union), 통합해서 쿼리하기 어려움 자식들을 하나로
        - 주의 : orm전문가 db설계전문가 둘 다 추천하지 않는 방법임 지양

* @MappedSuperclass
    - 여러 엔티티에서 공통적으로 사용하는 매핑 정보(등록일, 수정일)만 상속받는 방법
    - 부모추상클래스는 실제 db에 매핑되지않는다 entity가 아니다
    - 부모추상클래스는 단순히 자식클래스에 매핑필드 정보만 준다.
    - Member와 Seller 두 엔티티에 id와 name의 공통 필드가 있으면 이것만 빼서 부모추상클래스에 넣는 방법이다. 반복해서 넣기 귀찮을때 사용하는 방법일 수도 있다. 또는 자주 바뀌는 요구사항을 맞추기위해 유지보수 쉽게하기위해
    - 부모의 매핑정보를 재정의하려면 오버라이드하면 된다.

* 복합 키와 식별 관계 매핑
    - 테이터 베이스에서 식별자가 하나 이상일때 매핑하는 방법, DB설계시 식별관계와 비식별관계에 대해 
    - 식별관계와 비식별관계
        - 식별관계 : 부모로부터 기본키를 내려받아서 이를 자식테이블 기본키 + 외래키 두개의 역할로 쓸때 부모와 자식을 식별관계라고함
        - 비식별관계 : 자식의 키가 따로있고 부모로부터 받은 키는 외래키로만 사용, 선택적 비식별관계(외래키 null 가능), 필수적 비식별관계(부모키필수로 받아야한다!)
        - 최근 선호도 : 주로 비식별관계를 쓰고 필요한 곳에만 식별관계 사용
    - 복합키:비식별 관계일때 > deidentifiedRel
        - 그냥 하나의 엔티티에 @Id 두개 넣으면 안됨 매핑오류 발생
        - JPA는 복합키를 지원하기 위해 @IdClass, @EmbeddedId 라는 두가지 방법 지원
        - @IdClass : 관계형 테이터베이스에 가까운 방법
        - @EmbeddedId : 객체지향에 가까운 방법
    - 복합키:식별 관계일때 > identifiedRel

* 조인 테이블
    - 외래키 하나로 연관관계를 맺는 것 대신에 연결 테이블을 두는 방식을 사용할때, 이 연결 테이블을 매핑하는 방법.. 별로 안쓴다고함
* 엔티티 하나에 여러 테이블 매핑
    - 보통 엔티티 하나에 하나의 테이블을 매핑하는데, 여러 테이블을 매핑하는 방법, 별로 안쓴다고함


## ch08
* 프록시 & 연관관계 관리
* 프록시와 즉시로딩, 지연로딩
    - 객체는 객체 그래프로 연관된 객체들을 탐색, 그런데 객체가 데이터베이스에 저장되어 있으므로 연관된 객체를 마음껏 탐색하기는 어려움, JPA 구현체들은 이 문제를 해결하기 위해서 프록시라는 기술을 사용함. 프록시를 사용하면 연관된 객체를 처음부터 데이터베이스에서 조회하는것이 아니라, 실제 사용하는 시점에 데이터베이스에서 조회하는 것임. 하지만 자주사용하는 것이면은 조인을 사용해서 함께 조회하는것이 좋음. JPA는 즉시로딩과 지연로딩이라는 방법으로 두가지를 모두 지원함
    - 프록시
        - Team team = em.getReference(Team.class, "team1"); team.getId(); // 초기화되지않음
        - @Access(AccessType.PROPERTY) 설정인 경우에만 초기화하지않는다.
        - @Access(AccessType.FIELD) 설정인 경우에는 프록시 객체를 초기화한다.
        - 이 프록시를 사용하면 멤버를 생성하고 멤버에 연관관계를 주입할때 유용하게쓸수있다. 데이터베이스 접근 횟수를 줄일수있다.
        - em.getEnmtityManagerFactory().getPersistenceUnitUtil().isLoaded(object) 로 프록시가 초기화 됬는지 확인 가능하다
    - 즉시로딩
        - 멤버조회할때 연관된 팀도 가져옴
        - @ManyToOne(fetch = FetchType.EAGER)
    - 지연로딩
        - 연관된 엔티티를 실제로 사용할때 가져온다
        - @ManyToOne(fetch = FetchType.LAZY)
    - JPA의 기본전략
        - ~ToMany 일때는 지연
        - ~ToOne 일때는 즉시

* 영속성 전이와 고아 객체
    - JPA는 연관된 객체를 함께 저장하거나 함께 삭제할 수 있는 '영속성 전이'와 '고아 객체 제거' 라는 편리한 기능을 제공함
    - CascadeType 옵션
        - ALL : 전부적용
        - PERSIST : 영속 child에 부모넣고 부모 persist하면 자식도 자동으로 저장
        - MERGE : 병합
        - REMOVE : 삭제 부모만 없애도 자식도 다 없어짐
        - REFRESH : refresh
        - DETACH : detach
    - CasCade 옵션은 @OneToMany 안에 설정한다 ex(cascade = CascadeType.PERSIST)
    - 고아 객체 제거 orphanRemoval = true -> 부모엔티티의 자식s 엔티티에서 컬렉션에서 제거하면 삭제된다.

## ch09
* 값 타입
    - 기본값 타입
    - 임베디드 타입(복합 값 타입)
    - 값 타입과 불변 객체
    - 값 타입의 비교
    - 값 타입 컬렉션
* 기본값 타입
    - int, double, 래퍼클래스(Integer), String
* 임베디드 타입(복합 값 타입)
    - @Embeddable 어노테이션붙인 Period 클래스에 Date startDate, endDate; 선언해놓고 실제 Entity 클래스에서 Emgedded Period workPeriod; 식으로 쓰면 응집력있게 쓸수있다. 간단한 구조체 마냥 
* 컬렉션 값 타입 > ./ch09_collectionvalue
    - 값 타입을 하나 이상 저장하려면
    - ex) 좋아하는 음식들, 주소들 을 하나의 멤버에 연관

## ch10 > ./ch10_ooquery
* 객체지향쿼리언어 > simple_jpql
    - EntityManager.find(), a.getB().getC() 와 같은 단순한 검색만으로는 어플리케이션 개발 어려움
    - ORM은 테이블이 아닌 객체를 대상으로 개발하는 방식
    - 따라서 엔티티 객체를 대상으로 검색을 하는 방법이 필요 -> JPQL 등장
* JPQL
    - 테이블이 아닌 객체를 대상으로 검색하는 객체지향쿼리
    - SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지않는다.
* JPA가 공식지원하는 다양한 검색지원방식
    - JPQL(Java Persistence Query Language)
    - Criteria 쿼리(Criteria Query) : JPQL을 편리하게 작성하도록 도와주는 api, 빌더 클래스 모음
    - 네이티브 SQL : JPA에서 jpql대신 직접 SQL을 사용할 수 있다.
    - QueryDSL : Criteria 쿼리처럼 JPQL을 편하게 작성하도록 도와주는 빌더 클래스 모음, 비표준 오픈소스 프레임워크
    - JDBC 직접 사용 : MyBatis 같은 SQL 매퍼 프레임워크 사용 : 필요하면 JDBC를 직접 사용할 수 있다.
* 명심
    - QueryDSL, Criteria 도 JPQL을 편하게 사용하도록 도와주는 빌더클래스일뿐임
    - 중요한거는 JPQL
* 기본 사용
    - JPQL > jpql
        - jpql 키워드(select, 등)은 대소문자 구분 안하지만 엔티티 이름은 대소문자 구분함
        - 별칭은 필수 select m.userName from Member m 처럼 별칭 m같이 꼭 별칭 줘야함  
    - Criteria
        - Criteria 쿼리는 jpql을 자바코드로 작성하도록 도와주는 빌더 클래스api
        - 문법 오류를 컴파일 단계에서 잡을 수 있고 문자 기반의 jpql보다 안전하게 동적쿼리를 짤 수 있다.
        - 대신 코드가 복잡하고장황해서 직관적으로 이해가 힘들다 
    - QueryDSL > querydsl
        - Criteria보다 간결한 오픈소스 프로젝트
        - 공식문서 www.querydsl.com/static/querydsl/x.x.x/reference/ko-KR/html_signle/
    - Native SQL 사용 > nativeSql
        - JPA에서 지원하는 NativeSQL을 사용하면 DB고유의 기능도 사용가능하고 엔티티를 조회했을때 영속성 컨텍스트의 기능을 그대로 사용가능
* 심화
    - 벌크연산
        - 엔티티 다수를 수정할때 하나하나 바꾸면 시간이 너무 오래걸림
        - 여러 건을 한번에 삭제하거나 수정하는 벌크연산 필요 excuteUpdate()
        - int reusltCount = em.createQuery("수정 or 삭제 jpql").setParameter("amount", 10).executeUpdate();
        - db에 직접 요청하는 것이기 때문에 영속성 컨텍스트와 안맞을 수 있다. 
        - em.refresh(Entity) 로 다시 원하는 엔티티를 재조회하거나
        - 벌크연산을 가장먼저 실행하거나
        - 벌크연산 수행후 영속성 컨텍스트를 초기화하는 것이다.
    - 쿼리 후 영속상태
        - Member같은 엔티티를 조회하면 영속컨텍스트에서 관리하지만 단순값이나 member.address 같은것만 조회해오면 관리안한다.
    - em.find() vs JPQL
        - find는 영속컨텍스트에 찾고자하는게 있으면 그것을 준다(1차캐시라고함) 없으면 db조회
        - jpql은 무조건 db먼저 조회하고 조회한 값이 영속컨텍스트에 있는 값이면 버리고 영속컨텍스트에 있던 값 반환
    - jpql과 flush모드
        - em.setFlushMode(FlushModeType.AUTO); //커밋 또는 쿼리 실행시 플러시(기본값)
        - em.setFlushMode(FlushModeType.COMMIT); //커밋시에만 플러시
        - 만약 그 jpql 작업에만 플러쉬모드를 바꿔서쓰고싶다 하면 em.createQuery(~,~).setFlushMode(FlushModeType.?) 하면 된다
        - 성능최적화를 위해서 적절히 사용하자 
## ch11 
* 웹 어플리케이션 제작
* 단계
    - 프로젝트 환경설정
    - 도메인 모델, 테이블 설계
    - 어플리케이션 기능 구현
* 프로젝트 환경설정
    - Maven 설치 후 환경변수 path에 등록
* 스프링 설정 
    - web.xml, root-context, servlet-context => java config
    - 의존성 추가
    - datasource 설정
* 요구사항분석
* 테이블, 엔티티 설계 및 구축
* 비즈니스로직(레포지토리, 서비스) 구축 및 테스트
* 컨트롤러, 뷰 구축
* !!! 진행시 배웠던것 !!!
    - 서비스, 도메인모델
    - 도메인모델이 단순히 Entity로써 필드만 운용하는것이아니라
    - 핵심 비즈니스로직 (주문취소시 다른 엔티티에 재고량 다시 원복이랑 등등)이 들어가있으면 편하다
    - 서비스 레이어가 얇아질 수 있다

## ch12
* 스프링 데이터 JPA
    - 대부분의 데이터 접근 계층(Data Access Layer)은 CRUD로 부르는 유사한 등록, 수정, 삭제, 조회 등을 반복해서 개발해야함
    - jpa를 활용해도 똑같은 일이 발생 ex) save, findOne, findAll...
    - 제네릭과 상속을 적절히 구현하면 해결할 수 있으나 부모클래스에 너무 종속되게됨
    - Spring Data JPA는 스프링 프레임워크에서 jpa 를 편리하게 사용할 수 있도록 지원하는 프로젝트 위에 crud문제를 세련된 방법으로 해결
        - crud를 처리하기위한 공통 인터페이스 작성
        - 실행시점에 스프링 데이터 jpa 가 구현객체를 동적으로 생성해서 주입
        - public interface MemberRepository extends JpaRepository<Member, Long>{
        - Member findByUsername(String username) ... 등등}
        - 스프링 데이터 jpa 가 메소드 이름을 분석해서 적절하게 만들어줌;;
        - 스프링 데이터 jpa는 스프링데이터프로젝트(몽고, 레디스, 하둡 등도 다룸)의 하부 프로젝트중 하나
    - 도메인 클래스 바인딩 -> Controller파라미터에 Member, Order 로 받을수있게 -> 간편하게 제공해줌
    - 그외 검색조건등을 넘기는 명세전달 등등 편리한 방법 제공 
* 스프링 데이터 JPA와 QueryDSL 통합
    - 두가지 방법으로 QueryDSL 지원
        - org.springframework.data.querydsl.QueryDslPredicateExecutor
        - org.springframework.data.querydsl.QueryDslRepositorySupport
    - QueryDslPredicateExecutor
        - 첫번째 방법은 레포지토리에서 QueryDslPredicateExecutor를 상속받으면 된다.
        - ex) public interface ItemRepository extends JpaRepository<Item, Long>, QueryDslPredicateExecutor<Item>{}
        - 다만 join, fetch 를 사용할 수 없다. 사용하려면 JPAQuery를 직접 사용하거나 두번째방법 Support를 사용해야한다
    - QueryDslPredicateSupport
        - QueryDSL의 모든 기능을 사용하려면 JPAQuery 객체를 직접생성해서 사용하면된다
        - 이때 스프링 데이터 JPA가 제공하는 Support 를 상속 받아 사용하면 더 편리하게 사용 가능하다
        - 스프링 데이터 JPA가 제공하는 공통 인터페이스는 직접 구현할 수 없기 때문에 따로 클래스를 만들어서 Support를 상속받고 사용해야한다.\
        - ex) public class OrderRepositoryImpl extends QueryDslRepositorySupport implements CustomOrderRepository{...}
        - -> 생성자에서 super(Order.class); 로 엔티티클래스 정보를 넘겨줘야한다.
    - 이 책을 쓰신분의 생각으로는 spring + jpa 시 spring data jpa는 필수라고 생각하신다고함

        
## ch13
* 웹 어플리케이션과 영속성 관리
    - 12장 이전에 했던 j2se환경에서의 jpa 환경은 우리가 엔티티매니저생성하고 트랜잭션도 관리했어야했는데 
    - 스프링이나 j2ee 컨테이너 환경에서 jpa를 사용하면 컨테이너가 제공하느 전략을 따라야한다.
* 스프링 컨테이너의 기본전략
    - 트랜잭션 범위의 영속성 컨텍스트 전략 => 트랜잭션의 범위와 영속성 컨텍스트의 생존범위가 같다
    - filter interceptor -> controller -> view (준영속) / service -> repository (영속 및 트랜잭션 범위)
    - 보통 비즈니스로직을 시작하는 Service layer에 @Transactional을 붙여서 시작하는데 이 어노테이션이 있으면 이 안에 있는 메소드를 호출하기전에 스프링의 트랜잭션 AOP가 먼저 동작함
    - 이 스프링 트랜잭션 AOP는 호출하기전에 트랜잭션을 시작하고 대상 메소드가 종료되면 트랜잭션을 커밋하면서 종료함=> jpa는 영속성 컨텍스트를 플러시해서 변경 내용 DB에 반영 후 DB 트랜잭션 커밋
    - 만약 예외 발생하면 롤백하고 종료, 이때 플러시 호출 안함
    - ex) 컨트롤러에서 Service가 반환한 Member는 준영속상태임, 변경 감지와 지연로딩이 동작안하기때문에 미리 로딩이 안된 Team이 있을 경우에 Member.getTeam하면 예외 발생함
    - => 준영속상태에서 지연로딩문제를 해결하려면은 크게 두가지 방법이 있음
        - 뷰가 필요한 엔티티를 미리 로딩
        - OSIV를 사용해서 엔티티를 항상 영속상태로 유지하는 방법
    - 미리 로딩방법
        - 글로벌 패치 전략 수정
            - @ManyToOne(fetch = FetchType.EAGER)
            - private Member member;
            - order를 find하고 order.getMember().getName() // 이미 로딩된 데이터를 가져온다
            - 단점
            - N+1문제 발생, 사용하지않는 엔티티 로딩=> em.find(Order.class, id) 로 할때는 잘조회한다, JPQL을 사용해서 리스트를 가져올때 문제가 발생한다
            - jpql을 분석해서 sql생성 -> db조회 후 결과 반환 -> 반환된 Order엔티티안에 eager패치전략이 되있는 Member확인 -> Member또조회 -> 다음 Order엔티티도 확인 -> 또조회 .. n회 반복
            - => 패치 조인으로 해결 가능 ex) select o from Order o join fetch o.member -> 다만 프레젠테이션에 필요한 데이터가 다 다르므로 메소드를 두개 만들어야할수도있고 아니면 하나만 만들어서 한쪽이 살짝 비효율적으로 동작하도록 해야한다. 타협점을 잘 잡아야한다.
        - 프록시 객체 강제로 초기화
            - order.getMember().getname(); 강제로 초기화후 return order; 이것도 프레젠테이션계층의 요구에 따라 더 만들거나해서 비즈니스로직을 자꾸 간섭하게된다.
            - 프록시 초기화 계층을 따로 만들어야겠다 -> FACADE(퍼사드) 계층이 그 역할을 해줄것임 
            - ex) controller가 facade(초기화담당)를 호출하고 facade(초기화담당)는 service(원래기능)를 호출하고
            - 대신 계층 하나 더생기고 코드 더 적어야함..
        - 결론
            - 지금까지 나온 방법으로는 코드 더 작성하고 번거롭고 에러가 발생하기 쉬워서 LazyInitializationExeption을 만나게 될 확률이 높아진다.
            - 아무튼 모든 엔티티가 프레젠테이션계층에 있을때 준영속상태라는 것이다.
    - OSIV(Open Session In View)
        - 영속성 컨텍스트를 뷰까지 열어둔다는 뜻이다
        - 따라서 뷰에서도 지연 로딩을 사용할 수 있다.
        - OSIV는 하이버네이트에서 사용하는 용어고 JPA에서는 OEIV(Open EntityManager In View 이게 더 어감에 딱 맞는다) 라고 부른다. 관례상 둘다 OSIV라고 한다
        - 영속성컨텍스트가 요청 전체에 걸쳐 열려있고 당연히 영속상태도 전체고 트랜잭션도 전체다. 보안문제로 패스워드 블러처리하고 전송하려고 setPassword(null)하면 어김없이 db에 적용된다.
        - 해결점 => 프레젠테이션 계층에서 수정 못하게 막으면 된다.
            - 방법1 : 엔티티를 읽기 전용 인터페이스로 제공, 서비스에서 MemberView(getter만 있는 인터페이스)형태로 반환 Member는 MemberView를 구현해야함
            - 방법2 : 엔티티 래핑, 생성자로 엔티티를 받고 가지고있는 엔티티로 Getter만 있는 래퍼클래스를 만들어서 반환
            - 방법3 : DTO만 반환, 전통적인 방법 return 할때 DTO를 사용해서
            - 결론 : 코드양 증가함, 차라리 개발자들끼리 합의해서 프레젠테이션계층에서는 수정안하는게 더 나을 지경
            - 이런 단점으로 인해서 안쓰는 추세였다가 최근 이런 문제점을 보완해서 비즈니스 계층에서만 트랜잭션을 유지하는 방식의 OSIV를 사용한다.
            - 스프링 프레임워크가 제공하는 OSIV가 이 새로운 방식을 사용하는 OSIV다 
    - 스프링프레임워크 OSIV 라이브러리
        - 하이버네이트 OSIV 서블릿 필터
        - 하이버네이트 OSIV 스프링 인터셉터
        - JPA OEIV 서블릿 필터
        - JPA OEIV 스프링 인터셉터
        - 사용법
            - 원하는 클래스를 넷중에 선택해서 사용하면되는데, 서블릿 필터는 서블릿필터에 등록하면되고, 인터셉터에 등록해야하는건 인터셉터에 등록하면된다
        - 플로우
            - Filter, InterCeptor -> Controller -> Service -> Repository
            - (            영속상태 수정불가    )  (    영속상태 수정가능  )
            - Fileter나 Interceptor로 다시 돌아왔을때 flush() 안하고 close만 하고 영속성 컨텍스트만 닫는다.
        - 트랜잭션 없이 읽기 , 영속성 컨텍스트를 이용한 모든 변경은 트랜잭션 안에서 이루어져야함, 트랜잭션 없이 엔티티를 변경하고 영속성 컨텍스트를플러시 하면 TransactionRequiredException예외가 발생한다.
        - 주의사항 : Member member = service.getMember(); member.setName("xxx"); service.someBiz(); 하면 문제가 된다. member.setName()을 맨 뒤로 보내든가 해야한다.
            - 트랜잭션 롤백시 주의해야한다. 
            - 성능 튜닝시 확인해야할 부분이 넓다


## ch14
* 컬렉션과 부가기능
    - 컬렉션 : 다양한 컬렉션 특징 설명
    - 컨버터 : 엔티티의 데이터를 변환해서 데이터 베이스에 저장
    - 리스너 : 엔티티에서 발생한 이벤트 처리
    - 엔티티 그래프 : 엔티티 조회할때 연관된 엔티티들을 선택해서 함께 조회
* 컬렉션
    - JPA는 자바에서 기본으로 제공하는 Collection, List, Set, Map 컬렉션을 지원함
    - @OneToMany, @ManyToMany 혹은 @ElementCollection을 사용해서 값 타입을 하나 이상 보관할 때 사용
    - 종류
        - Collection : 자바가 제공하는 최상위 컬렉션, hibernate에서는 중복허용하고 순서보장안한다고 가정한다. ArrayLIst로 초기화
        - Set : 중복을 허용하지 않는 컬렉션, 순서를 보장하지는 않음. HashSet초기화
        - List : 순서가 있는 컬렉션, 순서를 보장하고 중복을 허용한다. ArrayList 초기화 
            - + @OrderColumn(name = "someThing") 하면 순서값을 저장해 조회할 때 사용((목표어레이엔티티)테이블에 position 컬럼 생성), 다음 단점때문에 실무에서 잘 사용하지않음
                - board, comment가 있다고 할때 commnet를 insert할때는 board entity에 있는 @OrderColumn 포지션값을 알수가없다. 그래서 나중에 update문이 하나 더생긴다.
                - List를 변경하면 연관된 많은 위치값을 변경해야한다.
                - 중간에 position값이 없으면 null pointer exception 발생
            - @OrderBy @OrderColumn대신 쓰는것
                - @OrderBy("username desc, id asc") private set<Member> members.... 식으로 사용
                - 실제로 조회할때 sql 문에 orderby 가 추가되서 간다 
        - Map : Key, Value 구조로 되어있는 특수한 컬렉션 
    - 하이버네이트에서 엔티티를 영속상태로 만들때 내부 원본컬렉션을 래핑하는 하이버네이트내장 컬렉션으로 바꾼다. 
* 컨버터 
    - 엔티티안에 vip 여부를 가리키는 boolean 타입이 있다고했을때 이게 실제 db에 저장될때는 db방언에 따라 1, 0 저장될텐데
    - 컨버터를 쓰면 Y, N 등으로 저장할 수 있다. ex) @Convert(converter=BooleanToYNConverter.class) => BooleanToYNConverter 클래스를 생성해야한다. AttributeConverter를 상속받는다
* 리스너 
    - 모든 엔티티를 대상으로 언제 어떤 사용자가 삭제를 요청했는지 모두 로그를 남겨야 하는 요구사항이 있다고 가정할때, 이 때 삭제로직마다 다 찾아서 로그를 남기는 것은 너무 비효율적임
    - JPA 리스너 기능을 사용하면 엔티티의 생명주기에 따른 이벤트를 처리할 수 있음
    - 이벤트 종류
        - PostLoad : 엔티티가 영속성 컨텍스트에 조회된 직후 또는 refresh를 호출한 후
        - PrePersist : persist() 메소드를 호출해서 영속성 컨텍스트에 관리하기 직전에 호출된다, 식별자 생성 전략을 사용할 경우 id는 없다
        - PreUpdate : flush()나 commit()을 호출해서 엔티티를 데이터베이스에 수정하기 직전에 호출된다
        - PreRemove: remove() 메소드를 호출해서 엔티티를 영속성 컨텍스트에서 삭제하기 직전에 호출된다. 삭제로 인한 영속성 전이가 일어날 때도 호출된다
        - PostPersist : flush나 commit을 호출해서 엔티티를 데이터베이스에 저장한 직후에 호출된다. 식별자 생성전략에따라(IDENTITY이면) persist()후에 바로 나타난다
        - PostUpdate : flush(), commit()을 호출해서 엔티티를 데이터베이스에 수정한 직후 호출
        - PostRemove : flush(), commit()을 호출해서 엔티티를 데이터베이스에 삭제한 직후에 호출
    - 이벤트 적용 위치
        - 엔티티에 직접 적용 : 엔티티안에 @PrePersist 등의 어노테이션 넣은후에 메소드에 로그저장로직을 넣는다
        - 별도의 리스너 등록 : 엔티티 @Entity @EntityListeners(DuckListener.class) 처럼 클래스로 따로 빼서 넣고 메소드에는 Obj또는 특정 타입의 매개변수를 받도록한다.
        - 기본 리스너 사용 : 또는 기본리스너를 설정에 적용하는 방법이 있다. xml or java config
* 엔티티 그래프 > ./ch12
    - 엔티티를 조회할때 연관된 엔티티들을 함께 조회하려면 글로벌 fetch 옵션을 EAGER로 설정하거나 JPQL 에서 패치조인을 사용하면 된다
    - 패치조인의 경우에는 주문상태를 검색, 주문과 회원(fetch join)을 모두 검색 등등 상황에따라 여러 JPQL쿼리를 작성해야한다. 
    - 이는 JPQL에 데이터 조회와 연관데이터조회기능을 한꺼번에 전가했기때문 
    - 엔티티 그래프를 사용하면 기본 쿼리 구문에서 fetch join을 우리가 쓸 필요없이 연관데이터를 편리하게 가져올 수 있다.
    - 방법에는 정적정의 Named엔티티 그래프, 동적정의 엔티티그래프가 있다.
    - 명심
        - root에서 시작 항상 조회하는 엔티티의 루트부터 시작
        - 이미 로딩된거는 어떻게 할수없음, 조회한 엔티티를 힌트 추가해서 또 조회하면 적용되지않음     
## ch15
* 고급 주제와 성능 최적화
    - 예외처리
    - 엔티티 비교 (위에서 한번 설명했던것임 , 만약 영속컨텍스트 다르면 euqals 비즈니스키 비교, 또는 데이터베이스 동등성(@id) 비교한다)
    - 프록시 심화 주제
    - 성능 최적화
* 예외 처리
    - JPA 표준 예외들은 javax.persistence.PersistenceException의 자식 클래스들임 이 부모클래스는 RuntimeException의 자식클래스임
    - JPA 표준 예외는 크게 두가지이다
        - 트랜잭션 롤백을 표시하는 예외 : 심각한 예외
        - 트랜잭션 롤백을 표시하지 않는 예외 : 개발자가 커밋할지 롤백할지 선택
    - 롤백을 표시하는 예외
        - EntityExistsException : persist() 호출시 이미 같은 엔티티가 있으면 발생
        - EntityNotFoundException : EntityManager.getReference() 를 호출했는데 실제 사용시 엔티티가 존재하지 않으면 발생
        - OptimisticLockException : 낙관적 락 충돌시 발생
        - PessimisticLockException : 비관적 락 충돌시 발생
        - RollbackException : EntityTransaction.commit() 실패시 발생, 롤백이 표시되어 있는 트랜잭션 커밋시에도 발생
        - TransactionRequiredException : 트랜잭션이 필요할때 트랜잭션이 없으면 발생, 트랜잭션 없이 엔티티를 변경할때 주로 발생 
    - 롤백을 표시하지않는 예외
        - NoResultException : Query.getSignleResult() 호출시 결과가 하나도 없을때 발생
        - NonUniqueResultException : Query.getSignleResult() 호출시 결과가 둘 이상일때 발생
        - LockTimeoutException : 비관적 락에서 시간 초과시 발생
        - QueryTimeoutException : 쿼리 실행 시간 초과 시 발생
    - 서비스 계층에서 데이터 접근계층의 구현 기술에 직접 의존하는 것은 좋은 설계라 할 수 없다. 예외도 마찬가지다. 그래서 스프링프레임워크는 데이터 접근계층에서 생긴 예외를 추상화해서 개발자에게 제공함
        - javax.persistence. -> org.springframework....
        - PersistenceException -> JpaSystemException
        - NoResultException -> EmptyResultDataAccessException
        - NonUniuqeResultExcepotion -> IncorrectResultSizeDataAccessException
        - LockTimeoutException -> CannotAcquireLockException
        - QueryTimeoutException -> QueryTimeoutExcpeiton
        - EntityExistsException -> DataIntegrityViolationExcpetion
        - EntityNotFoundException -> JpaObjectRetievalFailureException
        - OptimisticLockException -> JpaOptimisticLockingFailureException
        - PessimisticLockException -> PessimisticLockingFailureException
        - TransactionRequiredException -> InvalidDataAccessApiUsageException
        - RollbackException -> TransactionSystemException
    - JPA표준명세상 발생할수있는 다음 두 예외도 추상화해서제공함
        - java.lang.IllegalStateException -> InvalidDataAccessApiUsageException
        - java.lang.IllegalArgumentException -> InvalidDataAccessApiUsageException
    - 이처럼 추상화된 예외 사용하려면 PersistenceExceptionTranslationPostProcessor를 스프링빈으로 등록하면 된다.
    - java config 에서는 @Bean public PersistenceExceptionTranslationPostPorsessor tran() return new PersistenceExceptionTranslationPostProcessor();
    - rollback할때 db는 롤백되지만 영속성컨텍스트는 그대로다. 잘 닫아주거나, em.clear(), osiv 사용중이면은 문서참고하자
* 프록시 심화
    - 프록시객체와 실제 엔티티 객체의 비교
        - 동등성 비교할때는 == 비교 대신 instanceof사용(프록시는 원본을 상속받은 자식타입이므로)
        - 프록시의 멤버변수에 직접접근말고 대신에 접근자 메소드 사용
    - 상속관계와 프록시
        - Item을 상속받는 프록시객체를 Book용으로 사용한다거나 다운캐스팅하면 예외발생하거나 작동이 원하는대로안된다.
        - 해결법
            - 다형성을 포기하고 자식타입 직접 조회
            - 프록시 벗기기 unProxy() 함수로 원본엔티티 반환해서 사용
            - 또는 Item추상클래스 implemnts getTitle { } 식으로 공통된 기능을 제공하는 메소드 구현을 강제하는 방법도 있다.
            - 비지터 패턴 메소드 오버로딩을 사용해서 Book받으면 book일하고 Album 받으면 Album일하고 하는식으로 
* 성능 최적화 
    - 즉시로딩 n+1
        - 가장 주의해야할 문제
        - ex) @OneToMany(mappedBy= "member", fetch = FetchType.EAGER) private List<Order> orders  //class Member안임
        - em.find(Member.class, id)로 조회하면 정말 잘 작동한다 조인이용해서
        - 문제는 JPQL사용할때다
        - 조회할때 지연로딩, 즉시로딩 신경쓰지않고 일단 Member리스트 조회한다음에 ! 아 EAGER네? 하면서 조회해서나온결과물의갯수만큼또 sql을 보낸다. 
    - 지연로딩 n+1
        - 위 시나리오를 지연로딩으로 설정해도 자유롭지 않다
        - ex) @OneToMany(mappedBy= "member", fetch = FetchType.LAZY) private List<Order> orders    //class Member안임
        - 지연로딩으로 설정하면 일단 jpql로 리스트 조회했을때 n+1은 발생하지않는다
        - 이후 members.get(0).getOrders().size()는 괜찮은데
        - for(Member member : memberes) member.getOrders().size(); 하면 n+1문제 발생이다.
    - n+1문제 파훼법
        - 패치 조인 사용
            - select m from Member m join fetch m.orders > inner join기본사용 해서 한번에 잘 가져온다
            - 다만 일대다 조인을 했으므로 겨로가가 늘어나서 중복된 결과가 나타날수있는데 이때 JPQL의 DISTINCT를 사용해서 중복을 제거하는것이 좋다
        - 하이버네이트 @BatchSize
            - 이 어노테이션을 사용하면 지정한 size만큼 sql의 in절을 사용해서 조회한다. 만약 회원이 10명인데 size=5로 했으면 5명씩해서 2번의 sql만 추가로 실행한다.
            - 설정상으로 애플리케이션 전체이 기본 배치사이즈를 설정할 수도 있다. hibernate.default_batch_fetch_size
        - 하이버네이트 @Fetch(FetchMode.SUBSELECT)
            - 조회할때 서브쿼리를 사용해서 n+1문제를 해결한다.
        - n+1 정리
            - 지연로딩만을 사용하는것을 추천하고 성능 최적화가 꼭 필요한 곳에서는 JPQL 패치 조인을 사용하는 것이 좋다.
            - 글로벌 패치전략 기본값
                - one2one many2one 즉시로딩 > fetch=FetchType.LAZY 로 설정해서 지연로딩 전략으로 바꿔서 사용하자
                - one2many many2many 지연로딩
    
    - 읽기 전용 쿼리의 성능 최적화
        - 설명
            - 영속성 컨텍스트에 굳이 넣을 필요없는 단순 조회에서는 굳이 변경감지를 위한 스냅샷 인스턴스를 영속성 컨텍스트에 보관하고있을필요가없다
            - 읽기전용으로 엔티티를 조회하면 메모리 사용량을 최적화할수 있다.
        - 방법
            - 1. 스칼라 타입으로 조회
            - 2. 읽기 전용 쿼리 힌트 사용(하이버네이트 전용) jpql사용할때 query.setHint("org.hibernate.readOnly", true);
            - 3. 읽기 전용 트랜잭션 사용 @Transactional(readOnly = true) 자동 플러시를 끈다. 따라서 스냅샷비교와같은 동작을 하지않기때문에 성능향상이 있다.
            - 읽기만 하는 로직에서 엔티티 조회 최적화는 1번 또는 2번을 선택해서 적용하고 3번을 통해서 읽기전용 트랜잭션으로 설정해서 플러시같은거 끄면 성능향상에 좋다.
    - 배치 처리
        - 수백만 건의 데이터를 배치 처리해야 하는 상황이면은 영속성 컨텍스트에 엔티티가 쌓이면서 메모리 부족 오류가 발생한다. 이러한 배치처리는 적절한 단위로 영속성 컨텍스트를 초기화해야한다. 2차 캐시를 사용하고 있다면 2차 캐시에 엔티티를 보관하지 않도록 주의해야한다.
        - JPA 등록 배치
            - if(i % 100 == 0){em.flush(); em.clear();} 100건마다 플러시 및 영속성 컨텍스트 초기화
        - JPA 수정 배치
            - 100건씩 페이징 조회해서 가져오고, 가져온 데이터 처리하고 em.flush(), em.clear();
            - 하이버네이트 scroll 사용해서 한건한건 next() 로 가져올 수 있다.
            - 무상태 세션 ( 영속성 컨텍스트 없이) 수정할때 직접 update() 해줘야한다.
    - SQL 쿼리 힌트 사용
        - JPA는 데이터베이스 SQL힌트 기능을 제공하지 않는다. SQL힌트 기능을 사용하려면 하이버네이트를 직접 사용해야한다.
        - Session session = em.unwrap(Session.class); // 하이버 네이트 직접사용
        - List<Member> list = session .createQuery("sql").addQueryHint("FULL (MEMBER)").list(); // sql 힌트 추가 하이버네이트 4.3.10기준으로 오라클 방언만 등록되어있다. 다른거 쓰고싶으면 직접 getQueryHintString을 오버라이딩해서 기능 구현해야한다
    - 트랜잭션을 지원하는 쓰기 지연과 성능 최적화
        - hibernate.jdbc.batch_size 설정으로 em.persist() 가 뭐 x번째됬을때 한번 통신보내고 하는식으로 동작하게할수있다.
        - 다만 키 생성전략이 identity이면은 그전에 영속상태가 되려면 식별자가 필요한데 식별자를 얻기위해서 바로 sql이 db로 가기때문에 쓰기지연을 통한 성능최적화는 할 수 없다.
    

## ch16
* 트랜잭션과 락, 2차 캐시
