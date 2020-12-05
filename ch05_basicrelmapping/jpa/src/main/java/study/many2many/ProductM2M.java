package study.many2many;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class ProductM2M {
    
    @Id @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

    // 다대다 양방향 추가
    @ManyToMany(mappedBy = "products") // 역방향 추가
    private List<MemberM2M> members;



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

    
}
