package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public Store RegisterStoreInfo(@PathVariable Integer storeId) {
        return null;
    }
}
