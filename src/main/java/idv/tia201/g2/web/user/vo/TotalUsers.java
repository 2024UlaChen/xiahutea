package idv.tia201.g2.web.user.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "total_users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TotalUsers extends Core {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long totalUserId;
    private Integer userTypeId;     // 0:消費者 、 1:店家 、 3:管理員
    private Integer userId;
    private String username;
    private byte[] userImage;


}



