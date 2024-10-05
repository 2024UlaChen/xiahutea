package idv.tia201.g2.web.order.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Integer type;                // 訊息種類  1=訂單 2=爭議
    private Integer Id;                  // 訂單或爭議ID
    private Timestamp createDatetime;    // 建立日期

}