package idv.tia201.g2.web.store.controller;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.vo.Store;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static idv.tia201.g2.web.store.util.StoreToRegisterStore.convertPage;
import static idv.tia201.g2.web.store.util.StoreToRegisterStore.convertToRegisterStore;

@RestController
@RequestMapping("/registerstore")
public class RegisterStoreController {
    @Autowired
    private RegisterStoreService registerStoreService;

    @PostMapping("/register")
    public Store RegisterStore(Store store) {
        store.setSuccessful(false);
        store.setMessage("加入失敗");
        registerStoreService.register(store);
        return store;
    }

    @GetMapping("/registerstorelist")
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

    @GetMapping("/registerstoredetail")
    public RegisterStoreDTO RegisterStoreDetail(@RequestParam Integer storeId) {
        Store store = new Store();

        store.setStoreId(storeId);
        RegisterStoreDTO registerStoreDTO = registerStoreService.searchRegisterStoreDetail(store);
        return registerStoreDTO;
    }

    @PostMapping("/edit")
    public RegisterStoreDTO editRegisterStore(Store store) {
        try {
            Store save = registerStoreService.editRegisterStore(store);
            return convertToRegisterStore(save);
        } catch (MessagingException e) {
            store.setPassword("");
            store.setSuccessful(false);
            store.setMessage("寄信失敗");
            store.setStoreStatus(0);
            return convertToRegisterStore(store);
        } catch (IOException e) {
            store.setPassword("");
            store.setSuccessful(false);
            store.setMessage("附件失敗");
            store.setStoreStatus(0);
            return convertToRegisterStore(store);
        }

    }


}
