package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.service.StoreService;

import idv.tia201.g2.web.store.vo.Store;

import org.springframework.beans.factory.annotation.Autowired;

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

    private final StoreService storeService;
    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }
    @GetMapping("/home")
    public List<Store> Home(){
        List<Store> storeList =  storeService.findAll();
        return storeList;
    }
        @GetMapping("/storeinfo/{storeId}")
    public Store StoreInfo(@PathVariable Integer storeId){
        Store storeInfo =  storeService.findStoreById(storeId);
        return storeInfo;
    }


    @PostMapping("/update")
    public Store Update(@RequestBody Store store){
        Store storeData = storeService.saveStore(store);//update後丟回去瞧瞧 未完成
        return storeData;
    }

    @PostMapping("/login")
    public Store Login(@RequestBody Store store){

        return storeService.loginStore(store);
    }

    @PostMapping("/editpwd")
    public Store EditPwd(@RequestBody Store store){
        return storeService.editStorePassword(store);
    }
    @PostMapping("/editcard")
    public Store EditCard(@RequestBody Store store){
        return storeService.editStoreLoyaltyCard(store);
    }
    @PostMapping("/editstoreinfo")
    public Store EditStoreInfo(@ModelAttribute Store store){
        Store storeData = storeService.editStoreInfo(store);//update後丟回去瞧瞧 未完成
        return storeData;
    }




}
