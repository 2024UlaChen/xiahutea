package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.user.service.TotalUsersService;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/totalusers")
public class TotalUsersController {

    @Autowired
    TotalUsersService totalUsersService;

    @GetMapping
    public TotalUsers getTotalUser(HttpSession session){
        System.out.println((TotalUsers) session.getAttribute("totalUser"));
        return (TotalUsers) session.getAttribute("totalUser");



    }

}
