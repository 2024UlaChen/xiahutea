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
    @Column(name = "total_user_id")
    private Long totalUserId;
    @Column(name = "users_type_id")
    private Integer userTypeId;     // 0:消費者 、 1:店家 、 3:管理員
    @Column(name = "user_id")
    private Integer userId;

}



