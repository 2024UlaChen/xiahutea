package idv.tia201.g2.web.member.vo;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "customer")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @Column(name = "customer_account")
    private String customerAccount;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "valid_status")
    private Boolean validStatus;
    @Column(name = "customer_email")
    private String customerEmail;
    @Column(name = "customer_phone")
    private String customerPhone;
    @Column(name = "customer_money")
    private Integer customerMoney;
    @Column(name = "customer_carrier")
    private String customerCarrier;
    @Column(name = "customer_img")
    private byte[] customerImg;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "birthday")
    private Date birthday;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(Integer customerMoney) {
        this.customerMoney = customerMoney;
    }

    public String getCustomerCarrier() {
        return customerCarrier;
    }

    public void setCustomerCarrier(String customerCarrier) {
        this.customerCarrier = customerCarrier;
    }

    public byte[] getCustomerImg() {
        return customerImg;
    }

    public void setCustomerImg(byte[] customerImg) {
        this.customerImg = customerImg;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
