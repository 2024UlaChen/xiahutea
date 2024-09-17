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
    //商家名稱 (應該檢查名稱 不能重複 db梅索道)
    private String storeName;

    //註冊日
    private Timestamp registerDay;

    //商家地址
    private String storeAddress;

    private String vat;//統編

    private String email;//信箱

    private String contactPerson;//聯絡人

    private String contactPhone;//連絡電話

    private Integer storeStatus;//帳號狀態: 審核中 0  使用中 1  停權中 2

    private String owner;

    private String storeRemark;

}
