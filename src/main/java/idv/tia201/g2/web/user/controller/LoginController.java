package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.AdminService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private AdminService adminService;

//    @GetMapping("/login")
//    public Administrators Adminlogin(@RequestBody Administrators admin, HttpServletRequest request) {
//
//
//
//    }

}
