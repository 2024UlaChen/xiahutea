package idv.tia201.g2.web.member.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Arrays;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer", schema = "xiahu_db")
public class Member extends Core {

    private static final long serialVersionUID = 6017583930175390950L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_password")
    private String customerPassword;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "verify_code", insertable = false)
    private String verifyCode;

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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "customer_img", columnDefinition = "LONGBLOB")
    private byte[] customerImg;

    @Column(name = "sex")
    private String sex;

    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date birthday;

    @Column(name = "customer_remark")
    private String customerRemark;

    @Override
    public String toString() {
        return "Member{" +
                "customerId=" + customerId +
                ", customerPassword='" + customerPassword + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", verifyCode=" + verifyCode +
                ", validStatus=" + validStatus +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", customerMoney=" + customerMoney +
                ", customerCarrier='" + customerCarrier + '\'' +
                ", customerImg=" + Arrays.toString(customerImg) +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", customerRemark='" + customerRemark + '\'' +
                '}';
    }
}
