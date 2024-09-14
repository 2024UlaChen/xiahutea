package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

import static idv.tia201.g2.web.store.util.StoreToRegisterStore.convertPage;

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
    public Core RegisterStoreList(
            @RequestParam Integer storeStatus,
            @RequestParam(required = false) String vat,
            @RequestParam(required = false) String storeName,
            @RequestParam Integer page) {
        Store store = new Store();
        store.setStoreStatus(storeStatus);
        store.setVat(vat);
        store.setStoreName(storeName);

        Page<Store> stores = registerStoreService.searchRegisterStore(store, page);
        Page<RegisterStoreDTO> registerStores = convertPage(stores);
        Core core = new Core();
        core.setData(registerStores);
        return core;
    }
}
