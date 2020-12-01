
@Entity
public class Member{
    @Id @GeneratedValue
    private Long id;

    // 임베디드 타입 예제
    @Embedded
    private Address homeAddress;

    // 컬렉션 값 타입 예제
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOODS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<String>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<Adress> addressHistory = new HashSet<Adress>();


}