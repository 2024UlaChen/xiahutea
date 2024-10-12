package idv.tia201.g2.web.store.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.sql.Time;
import java.util.List;
/*
 @Temporal 的主要用法
@Temporal(TemporalType.DATE):
用於指定 java.util.Date 或
java.util.Calendar 類型的屬性僅存儲日期部分（年、月、日），
不包括時間部分。適合存儲像生日這樣的日期，但不關心具體的時間。

@Temporal(TemporalType.TIME): 用於指定 java.util.Date 或
* java.util.Calendar 類型的屬性僅存儲時間部分（時、分、秒），
* 不包括日期部分。適合存儲像營業開始時間這樣的時間點，但不關心具體的日期。

@Temporal(TemporalType.TIMESTAMP): 用於指定 java.util.Date 或
 java.util.Calendar 類型的屬性存儲完整的日期和時間部分（年、月、日、時、分、秒）。適合存儲需要完整日期和時間的情況，比如創建時間或最後更新時間。

* */

@Entity
@Table(name="store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store extends Core {
    private static final long serialVersionUID = 1062017833925367218L;
    //商家編號
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", updatable = false, nullable = false)
    private Integer storeId;
    //商家名稱 (應該檢查名稱 不能重複 db梅索道)
    @Column(name = "store_name", nullable = false, length = 20, unique = true)
    private String storeName;
    //註冊日
    @Column(name = "register_day")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp registerDay;
    //商家地址
    @Column(name = "store_address", nullable = false, length = 30)
    private String storeAddress;
    //商家電話
    @Column(name = "store_phone", length = 20, unique = true)
    private String storePhone;
    //開店時間
    @Column(name = "opening_hours")
    @Temporal(TemporalType.TIME)
    private Time openingHours;
    //關店時間
    @Column(name = "closing_hours")
    @Temporal(TemporalType.TIME)
    private Time closingHours;//關店時間
    //是否提供外送
    @Column(name = "is_delivery")
    private Boolean isDelivery;//是否外送
    //外送距離
    @Column(name = "delivery_distance")
    private Float deliveryDistance;//外送距離
    //外送金額
    @Column(name = "delivery_money")
    private Integer deliveryMoney;//外送金額

    @Column(name = "is_take_orders")
    private Boolean isTakeOrders;//是否接單

    @Column(columnDefinition = "float default 0")
    private Float score = 0f;;//評分


    @Column(name = "vat", nullable = false, length = 8, unique = true)
    private String vat;//統編



    @Column(name = "logo")
    private byte[] logo;//存圖片 Lob表示大物件 對應BLOB 或是 CLOB

    @Column(name = "email", nullable = false, length = 50)
    private String email;//信箱

    @Column(name = "contact_person", nullable = false, length = 10)
    private String contactPerson;//聯絡人

    @Column(name = "contact_phone", nullable = false, length = 20)
    private String contactPhone;//連絡電話

    @Column(name = "bank_code", length = 3)
    private String bankCode;

    @Column(name = "bank_account", length = 20)
    private String bankAccount;

    @Column(name = "store_status", nullable = false)
    private Integer storeStatus;//帳號狀態: 審核中 0  使用中 1 停權中 2   審核失敗 4

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "owner", nullable = false, length = 10)
    private String owner;

    @Column(name = "loyalty_card_name", length = 20, unique = true)
    private String loyaltyCardName;

    @Column(name = "exchange_rate")
    private Integer exchangeRate;
    //多少元一點

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp expiredDate;

    @Column(name = "valid_status")
    private Boolean validStatus;
    //集點卡狀態

    @Column(name = "store_remake")
    private String storeRemark;
    //商家備註

    @PrePersist
    protected void onCreate(){
        if(registerDay == null){
            registerDay = new Timestamp(System.currentTimeMillis());
        }
    }

    //實現雙向 商家和顧客集點紀錄為一對多 關連到store屬性
    // 非必要 不要關聯 除非一筆
//    @OneToMany(mappedBy = "store")
//    private List<CustomerLoyaltyCard> customerLoyaltyCards;

//補充Temporal 可以指定 儲存 年月日  或 時分秒  或二合一


}
