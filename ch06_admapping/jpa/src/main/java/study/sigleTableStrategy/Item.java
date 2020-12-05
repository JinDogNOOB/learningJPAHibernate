package study.sigleTableStrategy;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
* join이랑 클래스 구성은 다를게 없지만 
@Inheritance 어노테이션에 들어가는 값 single_table로 되어야하고
실제 db 테이블 각 컬럼에는 null허용 해줘야한다.
타입마다 쓰는게 있고 안쓰는게 있으니까
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {
    
    @Id @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name; // 이름
    private int price; // 가격
}


