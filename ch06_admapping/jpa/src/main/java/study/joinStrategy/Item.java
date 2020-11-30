package study.joinStrategy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 상속매핑은 @Inheritance 어노테이션을 붙여야한다 그리고 매핑전략으로는 여기서는 조인전략을 썼으므로 JOINED를 넣는다 
 * @DiscriminatorColumn 부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 자식 테이블을 구분할 수 있다.
 * 
 * 그리고 이 공통부모 클래스는 추상클래스로 선언
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name; // 이름
    private int price; // 가격
}


