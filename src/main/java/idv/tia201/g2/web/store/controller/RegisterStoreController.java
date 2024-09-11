package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registerstore")
public class RegisterStoreController {
    @Autowired
    private RegisterStoreService registerStoreService;

    @PostMapping
    public Store RegisterStore(Store store) {
        store.setSuccessful(false);
        store.setMessage("加入失敗");
        registerStoreService.register(store);
        return store;
    }

    @GetMapping("/registerStoreList")
    public List<Store> RegisterStoreList(
            @RequestParam Integer storeStatus,
            @RequestParam(required = false) String vat,
            @RequestParam(required = false) String storeName) {
        Store store = new Store();
        store.setStoreStatus(storeStatus);
        store.setVat(vat);
        store.setStoreName(storeName);


        return registerStoreService.searchRegisterStore(store);
    }
}
