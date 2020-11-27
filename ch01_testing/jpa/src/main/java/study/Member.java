package study;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

// mysql 에는 ID NAME AGE로 들어가있다

@Entity
@Table(name="MEMBER")
public class Member {
    
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name="NAME")
    private String username;

    // 매핑 정보가 없는 필드
    private Integer age;

    // ==추가==
    // 자바 enum을 이용한 필드는 다음과 같이 해야한다
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    // 날짜 타입 매핑은 Temporal 사용
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // 회원 설명하는 필드는 길이 제한이 없다 VARCHAR 대신 CLOB 사용해야한다
    // 다음과 같은 Lob을 쓰면 CLOB, BLOB 타입을 매핑할 수 있다.
    @Lob
    private String description;




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
