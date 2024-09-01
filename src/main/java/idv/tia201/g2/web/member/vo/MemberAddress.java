package idv.tia201.g2.web.member.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_address",schema = "xiahu_db")
public class MemberAddress extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_address_id")
    private Integer customerAddressId;

    @Column(name = "customer_id", updatable = false)
    private Integer customerId;

    @Column(name = "customer_address")
    private String customerAddress;

}
