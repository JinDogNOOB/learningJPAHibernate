package study.many2many;

import java.util.List;

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


        ProductM2M producta = new ProductM2M();
        producta.setId("producta");
        producta.setName("pa");
        em.persist(producta);


        MemberM2M member1 = new MemberM2M();
        member1.setId("member1");
        member1.setUsername("m1");
        member1.getProducts().add(producta);
        em.persist(member1);
        // 하나만 add 가능 두개 넣으면 에러준다;
        // 양방향 설정했으면 product.getMembers.add(member1); 가능하다

        // 탐색
        MemberM2M tempMember = em.find(MemberM2M.class, "member1");
        List<ProductM2M> products = tempMember.getProducts();
        for(ProductM2M p : products){
            System.out.println(p.getName());
        }

        

        tx.commit();
        em.close();
    }
}
