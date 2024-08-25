package idv.tia201.g2.web.user.vo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Administrators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id")
    private Integer administratorId;
    @Column(name = "admin_username")
    private String adminUsername;
    @Column(name = "admin_password")
    private String adminPassword;
}
