package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrator;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class
AdminController {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AdminService adminService;

//    @ResponseBody
    @PostMapping("/login")
    public TotalUsers adminlogin(Administrator admin, HttpServletRequest request) {
//        若無資料
        if (admin == null) {
            TotalUsers totalUser = new TotalUsers();
            totalUser.setMessage("請輸入用戶名稱及用戶密碼");
            totalUser.setSuccessful(false);
            return totalUser;
        }

        TotalUsers totalUser = adminService.login(admin);
        if (totalUser.isSuccessful()) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            session.setAttribute("loggedin", true);
            session.setAttribute("totalUser", totalUser);
        }
        return totalUser;
    }

}
