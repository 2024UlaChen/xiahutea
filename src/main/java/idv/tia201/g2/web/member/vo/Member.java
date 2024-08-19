package idv.tia201.g2.web.member.vo;

import java.sql.Date;

public class Member {
    private Integer customerId;
    private String customerAccount;
    private String customerPassword;
    private String nickname;
    private Date createDate;
    private Date updateDate;
    private Boolean validStatus;
    private String customerEmail;
    private String customerPhone;
    private Integer customerMoney;
    private String customerCarrier;
    private byte[] customerImg;
    private Integer sex;
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
