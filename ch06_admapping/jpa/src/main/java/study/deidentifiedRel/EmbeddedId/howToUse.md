// 저장
Parent parent = new Parent();
ParentId parentId = new ParentId("a", "b");
parent.setId(parentId);
parent.setName("name");
em.persist(parent);

// 조회
ParentId parentId = new ParentId("a", "b");
Parent parent = em.find(Parent.class, parentId);
