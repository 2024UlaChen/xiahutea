package idv.tia201.g2.web.store.vo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "store_calendar")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreCalendar {
    //處理商家休假

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( updatable = false, nullable = false)
    private Integer id;

    @Column(name="sotre_id")
    private Integer storeId;

    @Column(name = "store_holiday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date storeHoliday;

    //多對一
    @ManyToOne
    @JoinColumn(name = "sotre_id",insertable = false,updatable = false)
    private Store store;



}
