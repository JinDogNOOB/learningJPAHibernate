# 데이터베이스 테이블 상황
* PARENT
    - PARENT_ID1 (PK)
    - NAME

* CHILD
    - CHILD_ID (PK)
    - PARENT_ID1 (PK, FK)
    - NAME

* GRANDCHILD
    - GRANDCHILD_ID (PK)
    - CHILD_ID (PK, FK)
    - PARENT_ID1 (PK, FK)
    - NAME