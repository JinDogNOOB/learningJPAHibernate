package study.many2manywmid;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestDrive {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_unit");
    public void testSave(){
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();
            MemberMEM member = new MemberMEM();
            member.setId("m1");
            em.persist(member);

            ProductMEM product = new ProductMEM();
            product.setId("p1");
            em.persist(product);

            // 회원 상품 저장
            MemberProductMEM memberProduct = new MemberProductMEM();
            memberProduct.setMember(member);
            memberProduct.setProduct(product);
            memberProduct.setAmount(10);
            memberProduct.setOrderedDate(Calendar.getInstance().getTime());
            em.persist(memberProduct);

        tx.commit();
    }
}
