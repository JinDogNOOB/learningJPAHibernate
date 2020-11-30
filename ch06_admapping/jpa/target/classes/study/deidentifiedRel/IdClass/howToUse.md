
// 저장
Parent parent = new Parent();
parent.setId1("a");
parent.setId2("b");
parent.setName("name");
em.persist(parent);

// 조회
ParentId parentId = new ParentId("a", "b");
Parent parent = em.find(Parent.class, parentId);

