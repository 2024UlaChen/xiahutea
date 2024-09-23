package idv.tia201.g2.web.store.controller;


import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.service.StoreService;

import idv.tia201.g2.web.store.vo.Store;

import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.text.ParseException;
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
//        List<Store> storeList =  storeService.findAll();
        List<Store> storeList =  storeService.GetStoreList();
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
    public Store Login(HttpServletRequest request, @RequestBody Store store){
        Store data = storeService.loginStore(store);
        if(data.isSuccessful() ){
            if(request.getSession(false) != null){
                request.changeSessionId();
            }
            final HttpSession session = request.getSession();
            //設置session
            session.setAttribute("storeId", data.getStoreId());
            session.setAttribute("storeName", data.getStoreName());
            session.setAttribute("storeLogo",data.getLogo());
            session.setAttribute("loggedin",true);
            session.setAttribute("loginType","store");

           session.setAttribute("totalUser",storeService.GetTotalUser(data.getStoreId()));

//            session.setMaxInactiveInterval(3600);//秒為單位  Tomcat預設 30分
        }

        return data;
    }

    @GetMapping("/logout")
    public void Logout(HttpSession session){
        //登出
        session.removeAttribute("totalUser");


        session.removeAttribute("storeId");
        session.removeAttribute("storeName");
        session.removeAttribute("storeLogo");
        session.removeAttribute("loggedin");
        session.removeAttribute("loginType");
        session.invalidate();


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

    @GetMapping("/holidays/{storeid}")
    public List<Date> getDays(@PathVariable Integer storeid){
        return storeService.GetStoreHolidays(storeid);
    }

    @PostMapping("/storerest")
    public List<Store> StoreList(@RequestBody String date) throws ParseException {
        //列出yyyy-MM-dd沒有休息的店家
        return storeService.getStoreListWorking(date);
    }
    @PostMapping("/storeholiday")
    public void SaveHoliday(@RequestBody StoreViewModel data)  {
        storeService.addStoreHolidayByDate(data.getStoreId(),data.getHoliday());

    }

    @GetMapping("/updateStoreStatus/{storeId}")
    public void EditStoreStatus(HttpSession session,@PathVariable Integer storeId){

        if(checkAdminLogin(session)){
            storeService.editStoreStatus(storeId);
        }
    }







    public boolean IsStoreLogin(HttpSession session){
        TotalUsers data = (TotalUsers) session.getAttribute("totalUser");
        return data.getUserTypeId() == 1;
    }


    public boolean IsLogin(@SessionAttribute("loggedin") boolean status) {
        return status;
    }

    public boolean checkAdminLogin(HttpSession session){
        if(session.getAttribute("loggedin") != null && getLoginType(session).getUserTypeId()==3){
            return true;
        }
        return false;
    }


    public boolean checkStoreLogin(HttpSession session,Integer storeId){
        //登入中 是 商家登入 是 該商家 或是 管理員
        if(session.getAttribute("loggedin") != null){


            if(getLoginType(session).getUserTypeId()==3){
                return true;
            }
            boolean status = (boolean) session.getAttribute("loggedin");
            String loginType = (String) session.getAttribute("loginType");
            

            return IsLogin(status) && IsStoreLogin(session) && session.getAttribute("storeId").equals(storeId);


        }
        return false;

    }


    @GetMapping("GetLoginType")
    public TotalUsers getLoginType(HttpSession session){

        return (TotalUsers) session.getAttribute("totalUser");


    }




}
