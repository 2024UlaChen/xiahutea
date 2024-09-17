package idv.tia201.g2.web.store.controller;


import idv.tia201.g2.web.store.service.StoreService;

import idv.tia201.g2.web.store.vo.Store;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.text.ParseException;
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
    public Store StoreInfo(HttpSession session,@PathVariable Integer storeId){
        if(checkStoreLogin(session,storeId)){
            return storeService.findStoreById(storeId);
        }
        Store store = new Store();
        store.setSuccessful(false);
        return store;
    }


    @PostMapping("/update")
    public Store Update(HttpSession session, @RequestBody Store store)  {
        if(checkStoreLogin(session,store.getStoreId())){
            return storeService.saveStore(store);


        }
        return null;
    }

    @PostMapping("/login")
    public Store Login( HttpSession session, @RequestBody Store store){
        Store data = storeService.loginStore(store);
        if(data.isSuccessful() ){
            //設置session
            session.setAttribute("storeId", data.getStoreId());
            session.setAttribute("storeName", data.getStoreName());
            session.setAttribute("storeLogo",data.getLogo());
            session.setAttribute("loginStatus",true);
            session.setAttribute("loginType","store");

//            session.setMaxInactiveInterval(3600);//秒為單位  Tomcat預設 30分
        }

        return data;
    }

    @GetMapping("/logout")
    public void Logout(HttpSession session){
        //登出
        session.removeAttribute("storeId");
        session.removeAttribute("storeName");
        session.removeAttribute("storeLogo");
        session.removeAttribute("loginStatus");
        session.removeAttribute("loginType");

    }

    @PostMapping("/editpwd")
    public Store EditPwd(HttpSession session,@RequestBody Store store){
        if(checkStoreLogin(session,store.getStoreId())){
            return storeService.editStorePassword(store);
        }
        return null;
    }
    @PostMapping("/editcard")
    public Store EditCard(HttpSession session, @RequestBody Store store){
        if(checkStoreLogin(session,store.getStoreId())){
            return storeService.editStoreLoyaltyCard(store);
        }
        return null;
    }
    @PostMapping("/editstoreinfo")
    public Store EditStoreInfo(HttpSession session, @RequestBody Store store){
        if(checkStoreLogin(session,store.getStoreId())){
            return storeService.editStoreInfo(store);
        }
        return null;
    }
    @PostMapping("uploadLogo")
    public boolean uploadLogo(HttpSession session, @RequestParam("img")MultipartFile file,@RequestParam("storeId") Integer storeId) throws IOException {

        if(checkStoreLogin(session,storeId)){
            storeService.editLogoByStoreId(file,storeId);
            return true;
        }
        return false;

    }

    @GetMapping("/img/{storeid}")
    public byte[] Img(@PathVariable Integer storeid){
        return storeService.findLogoById(storeid);
    }

    @PostMapping("/storerest")
    public List<Store> StoreList(@RequestBody String date) throws ParseException {
        //列出yyyy-MM-dd沒有休息的店家
        return storeService.getStoreListWorking(date);
    }






    public boolean IsStoreLogin(@SessionAttribute("loginType") String loginData) {
        //看看是否為商家
        return loginData.equals("store");

    }
    public boolean IsLogin(@SessionAttribute("loginStatus") boolean status) {
        return status;
    }


    public boolean checkStoreLogin(HttpSession session,Integer storeId){
        //登入中 是 商家登入 是 該商家
        if(session.getAttribute("loginStatus") != null){
            boolean status = (boolean) session.getAttribute("loginStatus");
            String loginType = (String) session.getAttribute("loginType");

            return IsLogin(status) && IsStoreLogin(loginType) && session.getAttribute("storeId").equals(storeId);


        }
        return false;

    }


}
