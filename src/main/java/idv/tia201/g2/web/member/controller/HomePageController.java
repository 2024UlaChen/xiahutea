package idv.tia201.g2.web.member.controller;

import idv.tia201.g2.web.advertise.service.AdsService;
import idv.tia201.g2.web.advertise.vo.Advertise;
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
    private AdsService adsService;
    @Autowired
    public HomePageController(StoreService storeService,AdsService adsService) {
        this.storeService = storeService;
        this.adsService = adsService;
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

    @GetMapping("AdsList")
    public List<Advertise> getHomeAdsList()  {
        return adsService.getHomePageAdvertises();

    }

}
