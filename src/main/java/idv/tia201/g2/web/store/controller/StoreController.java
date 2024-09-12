package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.service.StoreService;

import idv.tia201.g2.web.store.vo.Store;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
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
    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;

    @Autowired
    public StoreController(StoreService storeService, RestTemplateAutoConfiguration restTemplateAutoConfiguration){
        this.storeService = storeService;
        this.restTemplateAutoConfiguration = restTemplateAutoConfiguration;
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
    public Store Update(@RequestBody Store store)  {
        Store storeData = storeService.saveStore(store);//update後丟回去瞧瞧 未完成

        return storeData;
    }

    @PostMapping("/login")
    public Store Login( HttpSession session, @RequestBody Store store){
        Store data = storeService.loginStore(store);
        if(data.isSuccessful() ){
            //設置session
            session.setAttribute("storeId", data.getStoreId());
            session.setAttribute("storeName", data.getStoreName());
            session.setAttribute("storeLogo",data.getLogo());
//            session.setMaxInactiveInterval(3600);//秒為單位  Tomcat預設 30分
        }

        return data;
    }

    @GetMapping("getSessionStore")
    public void GetSession(@SessionAttribute("storeName") String storeName) {
        //看看上下文現在啥款
        System.out.println(storeName);

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
    public Store EditStoreInfo(@RequestBody Store store){
        Store storeData = storeService.editStoreInfo(store);//update後丟回去瞧瞧
        return storeData;
    }
    @PostMapping("uploadLogo")
    public void uploadLogo(@RequestParam("img")MultipartFile file,@RequestParam("storeId") Integer storeId) throws IOException {
        //see see

            storeService.editLogoByStoreId(file,storeId);


            //file.transferTo(Paths.get("C:\\Users\\s5880\\Desktop\\TestUpload",file.getOriginalFilename()));

    }

    @GetMapping("/img/{storeid}")
    public byte[] Img(@PathVariable Integer storeid){
        return storeService.findLogoById(storeid);
    }


    @GetMapping("/test")
    public List<Store> test(){
        Date date = new Date(2024,6,25);
//        List<Store> list = storeService.getAllData();
        List<Store> list = storeService.getStoreListNoWorking(date);
//        List<Store> list = storeService.getAllStoreById();
        return list;
    }



}
