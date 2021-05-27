package study.many2many;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class MemberM2M {
    @Id @Column(name="MEMBER_ID")
    private String id;

    private String username;

    @ManyToMany
    @JoinTable(
        name = "MEMBER_PRODUCT_M2M",
        joinColumns = {@JoinColumn(name="MEMBER_ID")},
        inverseJoinColumns = {@JoinColumn(name = "PRODUCT_ID")} 
        )
    private List<ProductM2M> products = new ArrayList<ProductM2M>();




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

    public List<ProductM2M> getProducts() {
        return products;
    }

    public void setProducts(List<ProductM2M> products) {
        this.products = products;
    }



    
}
