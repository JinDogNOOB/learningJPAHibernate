package study;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

// mysql 에는 ID NAME AGE로 들어가있다

@Entity
@Table(name="MEMBER")
public class Member {
    
    // 기본키 값을 데이터베이스가 대신 만들어주는 auto increment같은 경우는 
    // @GeneratedValue 에 IDENTITY(기본키 생성을 db에 위임), SEQUENCE(데이터베이스 시퀸스를 사용해서 기본키 할당), TABLE(키생성 테이블 사용)
    // DB마다 전략이 다르니까 이거는 조사해보고 해야한다. 
    // 그리고 persistence.xml에 new_generator_mappings true로 해야한다. 옛날코드 유지보수해야한다면 false로
    // 여기서는 mysql auto increment쓰니까 IDENTITY로 근데 또,, 여기는 예제 String이라 패스!
    
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private String id;

    // not null, length 를 통해서 개발할때 사용하는 자동 테이블생성의 세부사항 적용가능하다 
    @Column(name = "NAME", nullable = false, length = 10)
    private String username;

    // 매핑 정보가 없는 필드
    private Integer age;

    // ==추가==
    // 자바 enum을 이용한 필드는 다음과 같이 해야한다
    // EnumType.ORDINAL 은 0 1 2식으로 저장 -> 대신 순서 바꾸면 안됨..
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 타입 매핑은 Temporal 사용
    // java.util.Date, java.util.Calendar 매핑할때 사용
    // Type에는 date, time, timestamp (날짜, 시간, 날짜시간)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 회원 설명하는 필드는 길이 제한이 없다 VARCHAR 대신 CLOB 사용해야한다
    // 다음과 같은 Lob을 쓰면 CLOB, BLOB 타입을 매핑할 수 있다.
    // CLOB : String, char[], java.sql.CLOB
    // BLOB: byte[], java.sql.BLOB
    @Lob
    private String description;

    //@Transient
    // 이 필드를 데이터베이스에 매핑하지 않는다



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    

}
