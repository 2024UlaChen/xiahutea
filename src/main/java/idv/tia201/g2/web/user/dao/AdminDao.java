package idv.tia201.g2.web.user.dao;

import idv.tia201.g2.web.user.vo.Administrator;
import org.springframework.web.bind.annotation.RequestParam;


public interface AdminDao {
    Administrator login(@RequestParam String username, String password);

}
