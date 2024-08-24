package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.store.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//RestController是組合註解 他等於Controller 加上 ResponseBody 就是一個RestController
//返回執會自動序列會為JSON給Clinet
@RestController
//RequestMapping設定跟URL 這個控制器必須從/store近來
@RequestMapping("store")
public class StoreController {
    //@Autowired：這個註解用來自動注入
    // Spring 容器中的 bean。Spring 會自動查找

    private TestService _test;
    @Autowired
    public StoreController(TestService _test){
        this._test = _test;
    }


    @GetMapping
    //GetMapping 專門處理GET
    @RequestMapping("/storelist")
    public String helloSpringBoot(){
        //商家列表
        return "Hello SpringBoot";
    }
    @GetMapping
    @RequestMapping("/try")
    public String TestBll(){
        String t = _test.hi();
        return t;
    }

    @PostMapping
    @RequestMapping("/subimt")
    public String TestSub(@RequestBody Store store){
        return "";

    }




}
