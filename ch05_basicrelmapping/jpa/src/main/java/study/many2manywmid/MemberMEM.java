package study.many2manywmid;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MemberMEM {
    
    @Id @Column(name= "MEMBER_ID")
    private String id;

    // 역방향
    @OneToMany(mappedBy = "member")
    private List<MemberProductMEM> memberProducts;



    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MemberProductMEM> getMemberProducts() {
        return memberProducts;
    }

    public void setMemberProducts(List<MemberProductMEM> memberProducts) {
        this.memberProducts = memberProducts;
    }


    
}
