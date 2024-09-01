package idv.tia201.g2.web.member.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "xiahu_db")
public class Member extends Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "customer_account")
    private String customerAccount;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "create_date", updatable = false)
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "valid_status", insertable = false)
    private Boolean validStatus;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_money", insertable = false)
    private Integer customerMoney;
    @Column(name = "customer_carrier")
    private String customerCarrier;
    @Column(name = "customer_img")
    private byte[] customerImg;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "birthday")
    private Date birthday;

}
