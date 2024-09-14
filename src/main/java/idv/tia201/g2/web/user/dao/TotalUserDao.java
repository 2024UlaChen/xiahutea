package idv.tia201.g2.web.user.dao;

import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TotalUserDao extends JpaRepository<TotalUsers,Integer> {

    TotalUsers findByUserTypeIdAndUserId(Integer userTypeId,Integer userId);
}
