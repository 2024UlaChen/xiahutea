package idv.tia201.g2.web.store.model;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
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
    private Date holiday;

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


}
