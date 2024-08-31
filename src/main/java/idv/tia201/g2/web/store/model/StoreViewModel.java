package idv.tia201.g2.web.store.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;


public class StoreViewModel {

    private Integer storeId;
    //商家名稱 (應該檢查名稱 不能重複 db梅索道)

    private String storeName;
    //註冊日

    private Timestamp registerDay;
    //商家地址

    private String storeAddress;
    //商家電話

    private String storePhone;
    //開店時間

    private Time openingHours;
    //關店時間

    private List<Timestamp> holidays;
    //休假日

    private Time closingHours;//關店時間
    //是否提供外送

    private Boolean isDelivery;//是否外送
    //外送距離

    private Float deliveryDistance;//外送距離
    //外送金額

    private Integer deliveryMoney;//外送金額


    private Boolean isTakeOrders;//是否接單


    private Float score;//評分


    private Boolean isCash;//是否支援現金


    private Boolean isCreditCard;//是否支援魔法卡


    private String vat;//統編


    private byte[] logo;//存圖片 Lob表示大物件 對應BLOB 或是 CLOB


    private String email;//信蕭


    private String contactPerson;//聯絡人


    private String contactPhone;//連絡電話


    private String bankCode;


    private String bankAccount;


    private Integer storeStatus;//帳號狀態


    private String password;


    private String owner;


    private String loyaltyCardName;


    private Integer exchangeRate;//多少元一點

    private Timestamp expiredDate;


    private Boolean validStatus;//集點卡狀態

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Timestamp getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(Timestamp registerDay) {
        this.registerDay = registerDay;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public Time getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Time openingHours) {
        this.openingHours = openingHours;
    }

    public List<Timestamp> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Timestamp> holidays) {
        this.holidays = holidays;
    }

    public Time getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(Time closingHours) {
        this.closingHours = closingHours;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean delivery) {
        isDelivery = delivery;
    }

    public Float getDeliveryDistance() {
        return deliveryDistance;
    }

    public void setDeliveryDistance(Float deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }

    public Integer getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(Integer deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }

    public Boolean getTakeOrders() {
        return isTakeOrders;
    }

    public void setTakeOrders(Boolean takeOrders) {
        isTakeOrders = takeOrders;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Boolean getCash() {
        return isCash;
    }

    public void setCash(Boolean cash) {
        isCash = cash;
    }

    public Boolean getCreditCard() {
        return isCreditCard;
    }

    public void setCreditCard(Boolean creditCard) {
        isCreditCard = creditCard;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Integer getStoreStatus() {
        return storeStatus;
    }

    public void setStoreStatus(Integer storeStatus) {
        this.storeStatus = storeStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLoyaltyCardName() {
        return loyaltyCardName;
    }

    public void setLoyaltyCardName(String loyaltyCardName) {
        this.loyaltyCardName = loyaltyCardName;
    }

    public Integer getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Timestamp getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Timestamp expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }
}
