package idv.tia201.g2.web.store.dto;

import idv.tia201.g2.core.pojo.Core;
import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterStoreDTO extends Core {
    private Integer storeId;
    //商家名稱
    private String storeName;

    //註冊日
    private Timestamp registerDay;

    //商家地址
    private String storeAddress;

    private String vat;//統編

    private String email;//信箱

    private String contactPerson;//聯絡人

    private String contactPhone;//連絡電話

    private Integer storeStatus;//帳號狀態: 申請中 0  使用中 1  停權中 2 申請失敗 4

    private String owner;//店家所有人

    private String storeRemark;//店家備註

}
