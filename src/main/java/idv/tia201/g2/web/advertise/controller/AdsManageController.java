package idv.tia201.g2.web.advertise.controller;

import idv.tia201.g2.web.advertise.service.AdsService;
import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/advertise/manage")
public class AdsManageController {
    @Autowired
    private AdsService adsService;

    //獲取目前登入使用者資料
    @GetMapping("/getrole")
    public TotalUserDTO getRole( HttpSession session){
        TotalUserDTO user = (TotalUserDTO) session.getAttribute("totalUserDTO");
        System.out.println("Session Attributes: " + Collections.list(session.getAttributeNames()));
        System.out.println("User from session: " + user);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in session");
        }
        return user;
    }
    //若為管理員拿全部廣告
    @GetMapping("/all")
    public List<Advertise> getAdvertises() {
        return adsService.findAllAdvertises();
    }
    //若為管裡員拿全部商店資料
    @PostMapping("/findstore")
    public List<Store> getallstore(@RequestBody List<Integer> userids ){
        return adsService.findByids(userids);
    }
    //若為商家拿商家自己的廣告
    @GetMapping("/{userid}")
    public List<Advertise> getstoreads(@PathVariable Long userid){
        return adsService.findstoreAdvertises(userid);
    }
    //若為商家拿自己的商店資訊
    @GetMapping("/findstore/{userid}")
    public Store getstore(@PathVariable Integer userid){
        return adsService.findStoreById(userid);
    }
}
