package idv.tia201.g2.web.advertise.dao;

import idv.tia201.g2.web.advertise.vo.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface AdsDao extends JpaRepository<Advertise,Integer> {
    public Advertise findBytitle(String title);
    public List<Advertise> findByAdsTotalUserid(Long adsTotalUserid);

    List<Advertise> findByHomeDisplayAndIsactiveAndStartTimeLessThanAndEndTimeGreaterThan(boolean homeDisplayIn, boolean isactiveIn, Timestamp start,Timestamp end );
    @Query("SELECT ad FROM Advertise ad WHERE ad.adsTotalUserid = :userid "
            + "AND ad.isactive = TRUE "
            + "AND ad.startTime < CURRENT_TIMESTAMP "
            + "AND ad.endTime > CURRENT_TIMESTAMP")
    Advertise findFirstActiveAdByUserId(@Param("userid") Long adsTotalUserid);
}
