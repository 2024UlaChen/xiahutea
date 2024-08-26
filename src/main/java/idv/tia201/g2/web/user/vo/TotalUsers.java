package idv.tia201.g2.web.user.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "total_users")
@Setter
@Getter
public class TotalUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "total_user_id")
    private Long totalUserId;
    @Column(name = "users_type_id")
    private Integer userTypeId;
    @Column(name = "user_id")
    private Integer userId;


}



