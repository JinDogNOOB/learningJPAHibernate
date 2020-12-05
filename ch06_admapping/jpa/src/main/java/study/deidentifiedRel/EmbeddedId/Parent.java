package study.deidentifiedRel.EmbeddedId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import study.deidentifiedRel.IdClass.ParentId;

@Entity
public class Parent {
    
    @EmbeddedId
    private ParentId id;

    private String name;
}
