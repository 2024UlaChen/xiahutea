package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("xiaHuTea")
public class HomePageController {
    private StoreService storeService;
    @Autowired
    public HomePageController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/home")
    public List<Store> GetStoreList() throws ParseException {
        //取得今天有上班的店家

        Date today = new Date();

        return storeService.getStoreListForHome(today);

    }

    @PostMapping("/search")
    public List<Store> SearchStoreList(@RequestBody String keyword) {
        return storeService.findStoreByName(keyword);

    }
    @PostMapping("/search/place")
    public List<Store> SearchStoreAddressList(@RequestBody String keyword) {
        return storeService.findStoreByAddress(keyword);

    }

}
