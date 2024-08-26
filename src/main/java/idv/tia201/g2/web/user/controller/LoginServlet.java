package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.UserLoginService;
import idv.tia201.g2.web.user.vo.Administrators;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginServlet extends HttpServlet {
    @Autowired
    private UserLoginService userLoginService;

    @GetMapping("/login")
    public void Adminlogin(@RequestParam Administrators admin) {

    }

}
