package study;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    
}
