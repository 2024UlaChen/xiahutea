package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.AdminService;
import idv.tia201.g2.web.user.vo.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AdminService adminService;

//    @ResponseBody
    @PostMapping("/login")
    public Administrator adminlogin(@RequestBody Administrator admin) {
        admin.setSuccessful(false);
        admin.setMessage("無會員資料");
        admin = adminService.login(admin);
        return admin;
    }

}
