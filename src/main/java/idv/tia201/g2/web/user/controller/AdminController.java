package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public void Adminlogin(@RequestBody Administrators admin) {
        admin.setSuccessful(false);
        admin.setMessage("無會員資料");
        adminService.login(admin);

    }

}
