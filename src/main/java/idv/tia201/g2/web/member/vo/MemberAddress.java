package idv.tia201.g2.web.member.vo;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_address")
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_address_id")
    private Integer customerAddressId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_address")
    private String customerAddress;

}
