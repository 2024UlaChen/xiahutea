package idv.tia201.g2.web.ads.vo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ads {
    @Id
    @Column(name = "ads_id",updatable = false)
    private Integer adsId;
    private String title;
    @Column(name = "ads_description")
    private String description;
    @Column(name = "img_url")
    private byte[] imgUrl;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "homepage_display")
    private Boolean homeDisplay;
    private Boolean isactive;
    @Column(name = "ads_total_users_id")
    private Long adsTotalUserid;
    @Column(name = "roletype_id")
    private int roleTypeId;

    @ManyToOne
    @JoinColumn(name = "ads_total_user_id", insertable = false, updatable = false)
    private total_users total_users;
}

