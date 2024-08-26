package idv.tia201.g2.web.store.vo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="customer_loyalty_card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoyaltyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loyalty_card_id", nullable = false, updatable = false)
    private Integer loyaltyCardId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Column(name = "points", nullable = false)
    private Integer points;

    @ManyToOne
    @JoinColumn(name = "store_id",insertable = false,updatable = false)
    private Store store;

//    @ManyToOne
//    @JoinColumn(name="customer_id",insertable = false,updatable = false)
//    private Member member;
    //Member實體 要建立一個 mappedBy = "member" 搭起雙向
    /*
    以下是Member實體
    @OneToMany(mappedBy = "member")
    private List<CustomerLoyaltyCard> customerLoyaltyCards
    * */

}
