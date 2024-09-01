package idv.tia201.g2.web.user.service.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    public Administrators login(Administrators admin) {
        final String username = admin.getAdminUsername();
        final String password = admin.getAdminPassword();

        if (username == null) {
            admin.setMessage("使用者名稱未輸入");
            admin.setSuccessful(false);
            return admin;
        }

        if (password == null) {
            admin.setMessage("密碼未輸入");
            admin.setSuccessful(false);
            return admin;
        }

        admin = adminDao.selectForLogin(username, password);
        if (admin == null) {
            admin = new Administrators();
            admin.setMessage("使用者名稱或密碼錯誤");
            admin.setSuccessful(false);
            return admin;
        }

        admin.setMessage("登入成功");
        admin.setSuccessful(true);
        return admin;
    }
}
