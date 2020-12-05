package study.many2manywmid;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(MemberProductMEMID.class)
public class MemberProductMEM {
    /*  
    이 회원 상품 엔티티는 기본키가 멤버, 프로덕트아이디로 이루어진 복합 기본키로 이루어져있다
    
    */
    
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private MemberMEM member;

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private ProductMEM product;

    private int amount;
    private Date orderedDate;









    public MemberMEM getMember() {
        return member;
    }

    public void setMember(MemberMEM member) {
        this.member = member;
    }

    public ProductMEM getProduct() {
        return product;
    }

    public void setProduct(ProductMEM product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }
    
    
}
