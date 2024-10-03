package idv.tia201.g2.web.advertise.dao;

import idv.tia201.g2.web.advertise.vo.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface AdsDao extends JpaRepository<Advertise,Integer> {
    public Advertise findBytitle(String title);
    public List<Advertise> findByAdsTotalUserid(Long adsTotalUserid);
    List<Advertise> findByHomeDisplayInAndIsactiveInAndStartTimeBetween(boolean homeDisplayIn, boolean isActiveIn, Timestamp startTime, Timestamp endTime );
}
