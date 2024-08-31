package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.service.impl.StoreServiceImpl;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.store.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//RestController是組合註解 他等於Controller 加上 ResponseBody 就是一個RestController
//返回執會自動序列會為JSON給Clinet
@RestController
//RequestMapping設定跟URL 這個控制器必須從/store近來
@RequestMapping("store")
public class StoreController {
    //@Autowired：這個註解用來自動注入
    // Spring 容器中的 bean。Spring 會自動查找

    private final StoreService _storeService;
    @Autowired
    public StoreController(StoreService _storeService){
        this._storeService = _storeService;
    }
    @GetMapping("/home")
    public List<Store> Home(){
        List<Store> storeList =  _storeService.findAll();
        return storeList;
    }
        @GetMapping("/storeinfo/{storeId}")
    public Store StoreInfo(@PathVariable Integer storeId){
        Store storeInfo =  _storeService.findStoreById(storeId);
        return storeInfo;
    }


    @PostMapping("/Update")
    public Store Update(@RequestBody Store store){
        Store storeData = _storeService.saveStore(store);//update後丟回去瞧瞧
        return storeData;
    }






}
