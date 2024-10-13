package idv.tia201.g2.web.advertise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseDto {
    private Integer adsId;                // 廣告 ID
    private String title;                 // 廣告標題
    private String description;           // 廣告描述
    private String imgUrl;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")// 圖片的 byte 陣列
    private Timestamp startTime;          // 開始時間
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp endTime;            // 結束時間
    private Boolean homeDisplay;          // 是否在首頁顯示
    private Boolean isactive;              // 廣告是否啟用
    private long adsTotalUserId;
    private Integer roleTypeId;
}
