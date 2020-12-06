package com.jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jpabook.jpashop.domain.Item;

import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
    
    @PersistenceContext
    EntityManager em;

    public void save(Item item){
        if (item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }


}
