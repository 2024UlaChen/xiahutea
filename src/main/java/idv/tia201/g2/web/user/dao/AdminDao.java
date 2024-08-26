package idv.tia201.g2.web.user.dao;

import idv.tia201.g2.web.user.vo.Administrators;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;


public interface AdminDao {
    public Administrators selectForLogin(@RequestParam String username, String password);

}
