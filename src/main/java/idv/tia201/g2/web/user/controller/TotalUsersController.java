package idv.tia201.g2.web.user.controller;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.service.TotalUsersService;
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

    @Autowired
    StoreService storeService;

    @GetMapping
    public TotalUserDTO getTotalUser(HttpSession session){
        TotalUserDTO user = (TotalUserDTO) session.getAttribute("totalUserDTO");
        if (user == null){
            user = new TotalUserDTO();
            user.setSuccessful(false);
            return user;
        }
        if(user.getUserTypeId() == 1){
            Store storeById = storeService.findStoreById(user.getUserId());
            if(storeById != null){
                user.setData(storeById.getStoreName());
            }
        }
        user.setSuccessful(true);
        return user;
    }

}
