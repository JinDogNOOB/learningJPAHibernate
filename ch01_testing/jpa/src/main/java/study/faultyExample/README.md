* 문제점
    - 데이터 중심의 설계였다
    - 외래키를 그대로 객체설계에 가져온게 문제
    - 객체에는 조인이 없다. 참조가 대신 있다
    - 따라서 객체는 외래 키 대신에 참조를 사용해야한다.
    - 이 상태로도 사용이 가능하겠지만 객체지향적이지 않다
    - 다음장부터 어떻게 해결해야하는지 보자
