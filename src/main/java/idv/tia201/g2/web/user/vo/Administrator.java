package idv.tia201.g2.web.user.vo;

import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Administrators")
public class Administrator extends Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrator_id")
    private Integer administratorId;
    @Column(name = "admin_username")
    private String adminUsername;
    @Column(name = "admin_password")
    private String adminPassword;
}
