package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("xiaHuTea")
public class homePageController {
    private StoreService storeService;
    @Autowired
    public homePageController(StoreService storeService) {
        this.storeService = storeService;
    }

    @RequestMapping("home")
    public List<Store> GetStoreList() throws ParseException {
        //取得今天有上班的店家

        Date today = new Date();

        return storeService.getStoreListForHome(today);

    }

}
