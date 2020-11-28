package study.many2one;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="team_tb")
public class Team {
    
    @Id
    @Column(name="team_id")
    private String id;

    private String name;

    // 양방향을 위한 
    // 양방향은 단방향 + 단방향으로 jpa에서 이루어짐
    // 원투매니를 추가
    // mappedBy에는 반대쪽 매핑의 필드이름 Team team; 이면은 team 
    // 을 준다. (mappedBy는 연관관계의 주인에 관계..)

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<Member>();
    /*
        엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래키는 하나다.
        둘 사이에 차이가 발생한다..
        따라서 두 연관객체중 하나를 정해서 테이블의 외래키를 관리해야하는데 이것을 연관관계의 주인이라고함

    */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    
}
