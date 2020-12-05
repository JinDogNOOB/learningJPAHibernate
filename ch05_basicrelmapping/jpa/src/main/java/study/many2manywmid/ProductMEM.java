package study.many2manywmid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductMEM {
    
    @Id @Column(name = "PRODUCT_ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // 여기서는 제품정보로 회원상품 전체 그래프 탐색 기능이 필요하지 않다고 해서 안넣었음
    // 그래프탐색하려면은 여기도 Member와 똑같이 해라



    
}
