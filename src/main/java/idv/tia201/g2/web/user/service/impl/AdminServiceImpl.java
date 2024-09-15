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
        String password = admin.getAdminPassword();
        TotalUserDTO totalUserDTO = new TotalUserDTO();

//        如果 username 沒填
        if (username.isEmpty()) {
            totalUserDTO.setMessage("使用者名稱未輸入");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }
//        如果 password 沒填
        if (password.isEmpty()) {
            totalUserDTO.setMessage("密碼未輸入");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }
//        進入 DAO 利用 username & password 找 admin
        admin = adminDao.login(username, password);
//        如果回傳 null 代表登入失敗，如果不是 null 代表 登入成功
        if (admin == null) {
            totalUserDTO.setMessage("使用者名稱或密碼錯誤");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }else {
            TotalUsers totalUser = totalUserDao.findByUserTypeIdAndUserId(userTypeId, admin.getAdministratorId());
            BeanUtils.copyProperties(totalUser, totalUserDTO);
//            totalUserDTO.setTotalUserId(totalUser.getTotalUserId());
            totalUserDTO.setMessage("登入成功");
            totalUserDTO.setSuccessful(true);
            return totalUserDTO;
        }
    }
}
