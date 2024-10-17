package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.core.util.EncrypSHA;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public TotalUserDTO adminlogin(Administrator admin, HttpServletRequest request, HttpServletResponse response) {
//        若無資料
        if (admin == null) {
            TotalUserDTO totalUserDTO = new TotalUserDTO();
            totalUserDTO.setMessage("請輸入用戶名稱及用戶密碼");
            totalUserDTO.setSuccessful(false);
            return totalUserDTO;
        }

        TotalUserDTO totalUserDTO = adminService.login(admin);

//        設置Session & Cookie
        if (totalUserDTO.isSuccessful()) {
            if (request.getSession(false) != null) {
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            session.setAttribute("loggedin", true);
            session.setAttribute("totalUserDTO", totalUserDTO);

            // 是否有勾選記住登入資料
            Boolean rememberMe = Boolean.valueOf(String.valueOf(admin.getData()));
            System.out.println(rememberMe);
            if (rememberMe){
                // 設置一個 Cookie
                String SHAUserneme = EncrypSHA.SHAEncrypt(admin.getAdminUsername());
                String SHAPassword = EncrypSHA.SHAEncrypt(admin.getAdminPassword());
                Cookie usernameCookie = new Cookie("username", SHAUserneme);
                Cookie passwordCookie = new Cookie("password", SHAPassword);
                // 設置過期時間（秒）
                usernameCookie.setMaxAge(60 * 60 * 24 * 7); // 1周
                passwordCookie.setMaxAge(60 * 60 * 24 * 7); // 1周
                // 設置路徑
                usernameCookie.setPath("/");
                passwordCookie.setPath("/");
                // 發送給 client 端
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }else {
                Cookie usernameCookie = new Cookie("username", "");
                Cookie passwordCookie = new Cookie("password", "");
                usernameCookie.setMaxAge(0);
                passwordCookie.setMaxAge(0);
                usernameCookie.setPath("/");
                passwordCookie.setPath("/");
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }
        }

        return totalUserDTO;
    }

}
