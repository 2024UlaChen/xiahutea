package idv.tia201.g2.web.user.dao;

import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalUserDao extends JpaRepository<TotalUsers,Integer> {

    TotalUsers findByUserTypeIdAndUserId(Integer userTypeId,Integer userId);

    TotalUsers findBytotalUserId(Long totalUserId);
}
