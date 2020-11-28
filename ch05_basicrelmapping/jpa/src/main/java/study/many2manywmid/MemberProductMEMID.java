package study.many2manywmid;

import java.io.Serializable;

// 회원상품 식별자 클래스
public class MemberProductMEMID implements Serializable {
    private String member; // MemberProduct.member 와 연결
    private String product; // MemberProduct.product 와 연결


    
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    // hashCode , equals 오버라이드 
    
}
