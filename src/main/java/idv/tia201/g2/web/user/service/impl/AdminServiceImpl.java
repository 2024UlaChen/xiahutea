package idv.tia201.g2.web.user.service.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    public Administrator login(Administrator admin) {
//        取得會員資料
        String username = admin.getAdminUsername();
        String password = admin.getAdminPassword();
//        如果 username 沒填
        if (username == null) {
            admin.setMessage("使用者名稱未輸入");
            admin.setSuccessful(false);
            return admin;
        }
//        如果 password 沒填
        if (password == null) {
            admin.setMessage("密碼未輸入");
            admin.setSuccessful(false);
            return admin;
        }
//        進入 DAO 利用 username & password 找 admin
        admin = adminDao.login(username, password);
//        如果回傳 null 代表登入失敗，如果不是 null 代表 登入成功
        if (admin == null) {
            admin = new Administrator();
            admin.setMessage("使用者名稱或密碼錯誤");
            admin.setSuccessful(false);
            return admin;
        }else {
            admin.setMessage("登入成功");
            admin.setSuccessful(true);
            return admin;
        }
    }
}
