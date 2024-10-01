package idv.tia201.g2.web.order.dto;

import lombok.*;
import java.sql.Timestamp;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private int orderId;
    private Timestamp orderCreateDatetime;

}