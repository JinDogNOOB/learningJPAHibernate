package study.deidentifiedRel.IdClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Id클래스의 id속성명과 여기 Parent의 id속성명이 똑같아야한다
 */
@Entity
@IdClass(ParentId.class)
public class Parent {

    @Id
    @Column(name = "PARENT_ID1")
    private String id1;

    @Id
    @Column(name = "PARENT_ID2")
    private String id2;

    private String name;
    
}
