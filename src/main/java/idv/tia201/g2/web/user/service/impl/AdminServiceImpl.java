package idv.tia201.g2.web.user.service.impl;

import idv.tia201.g2.web.user.dao.AdminDao;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrator;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static idv.tia201.g2.core.util.EncrypSHA.SHAEncrypt;
import static idv.tia201.g2.web.store.util.PasswordUtil.checkPassword;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private TotalUserDao totalUserDao;

    private Integer userTypeId = 3;

    public TotalUserDTO login(Administrator admin) {
//        取得會員資料
        String username = admin.getAdminUsername();
        TotalUserDTO totalUserDTO = new TotalUserDTO();
        String password = admin.getAdminPassword();

//        如果 username 或 password 沒填
        if (username.isEmpty() || password.isEmpty()) {
            totalUserDTO.setMessage("請輸入用戶名稱及用戶密碼");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }

        //檢驗帳號
        if(!checkPassword(username)){
            totalUserDTO.setMessage("使用者名稱或密碼錯誤");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }

        //檢驗密碼
        if(!checkPassword(password)){
            totalUserDTO.setMessage("使用者名稱或密碼錯誤");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }

//        密碼加密
        String SHAPassword = SHAEncrypt(password);
//        進入 DAO 利用 username & password 找 admin
        admin = adminDao.login(username, SHAPassword);

//        如果回傳 null 代表登入失敗，如果不是 null 代表 登入成功
        if (admin == null) {
            totalUserDTO.setMessage("使用者名稱或密碼錯誤");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }
        // 登入成功
        TotalUsers LoginTotalUser = totalUserDao.findByUserTypeIdAndUserId(userTypeId, admin.getAdministratorId());
        BeanUtils.copyProperties(LoginTotalUser, totalUserDTO);
        totalUserDTO.setMessage("登入成功");
        totalUserDTO.setSuccessful(true);
        return totalUserDTO;

    }
}
