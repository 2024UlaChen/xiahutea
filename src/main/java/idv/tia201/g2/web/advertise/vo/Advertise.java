package idv.tia201.g2.web.advertise.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import idv.tia201.g2.core.pojo.Core;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Advertise extends Core {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ads_id",updatable = false)
    private Integer adsId;
    private String title;
    @Column(name = "ads_description")
    private String description;
    @Column(name = "img_url")
    private byte[] imgUrl;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    private Timestamp startTime;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "GMT+8")
    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name = "homepage_display")
    private Boolean homeDisplay;
    private Boolean isactive;
    @Column(name = "ads_total_users_id")
    private Long adsTotalUserid;
    @Column(name = "roletype_id")
    private int roleTypeId;
//
//    @ManyToOne
//    @JoinColumn(name = "ads_total_users_id", insertable = false, updatable = false)
//    private TotalUsers totalusers;
}

