package idv.tia201.g2.web.order.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
public class OStoreDto {

    private Integer storeId;        //商家名稱
    private Timestamp registerDay;  //商家地址
    private String storeAddress;    //商家電話
    private Float score;            //評分
    private String vat;             //統編
    private byte[] logo;            //存圖片 Lob表示大物件 對應BLOB 或是 CLOB

}