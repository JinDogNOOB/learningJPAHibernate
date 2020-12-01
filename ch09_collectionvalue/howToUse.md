// 등록
Member member = new Member();

]임베디드 값 타입
member.setHomeAddress(new Address("통영", "몽돌해수욕장", "660-1023"));

// 기본값 타입 컬렉션
member.getFavoriteFoods().add("짬뽕");
member.getFavoriteFoods().add("짜장면");
member.getFavoriteFoods().add("탕수육");
member.getFavoriteFoods().add("라면");

//임베디드 값 타입 컬렉션
member.getAddressHistory().add(newAddress("서울", "강남", "123-1234"));
member.getAddressHistory().add(newAddress("서울", "강북", "532-5732"));

저장
em.persist(member);

// Cascade, orphan remove 가 기본적으로 설정되있다고 생각하면 된다.


## 주의
* 값 타입 컬렉션에 변경사항이 있으면 원래있던것들도 다시 저장한다. 그래서 매핑된 테이블의 데이터가 많다면 값타입컬렉션대신 기존의 일대다 관계를 적용하는것을 생각해야한다