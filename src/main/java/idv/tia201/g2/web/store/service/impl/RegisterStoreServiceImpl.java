package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.util.StoreToRegisterStore;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static idv.tia201.g2.web.store.util.VatUtil.isValidTWBID;

@Service
public class RegisterStoreServiceImpl implements RegisterStoreService {
    @Autowired
    private StoreDao storeDao;

    @Override
    public Store register(Store store) {
        //必填欄位
        if (store.getContactPerson().isEmpty()) {
            store.setMessage("請輸入聯絡人資料");
            return store;
        }
        if (store.getEmail().isEmpty()) {
            store.setMessage("請輸入E-mail");
            return store;
        }
        if (store.getContactPhone().isEmpty()) {
            store.setMessage("請輸入聯絡人電話");
            return store;
        }
        if (store.getStoreName().isEmpty()) {
            store.setMessage("請輸入店家名稱");
            return store;
        }
        if (store.getStoreAddress().isEmpty()) {
            store.setMessage("請輸入店家地址");
            return store;
        }
        if (store.getOwner().isEmpty()) {
            store.setMessage("請輸入店家負責人");
            return store;
        }
        if (store.getVat().isEmpty()) {
            store.setMessage("請輸入統一編號");
            return store;
        }

        //電話驗證
        String phonePattern = "(\\d{2,3}-?|\\(\\d{2,3}\\))\\d{3,4}-?\\d{4}|09\\d{2}(\\d{6}|-\\d{3}-\\d{3})";
        boolean phoneMatcher = Pattern.matches(phonePattern, store.getContactPhone());
        if (!phoneMatcher) {
            store.setMessage("聯絡電話錯誤");
            return store;
        }

        //統編驗證
        if(!isValidTWBID(store.getVat().trim())){
            store.setMessage("統一編號錯誤");
            return store;
        }

        //E-mail驗證
        String emailPattern = "^(.+)@(.+)$";
        boolean emailMatcher = Pattern.matches(emailPattern, store.getEmail());
        if (!emailMatcher) {
            store.setMessage("信箱錯誤");
            return store;
        }

        //依照統一編號查詢是否已是會員
        Store oldStore = storeDao.findByVat(store.getVat());
        if(oldStore == null){
            store.setStoreStatus(0);//狀態預設為0
            Timestamp registerDay = new Timestamp(System.currentTimeMillis());
            store.setRegisterDay(registerDay);
            storeDao.save(store);
            store.setSuccessful(true);
            store.setMessage("成功加入會員");
        }else {
            store.setMessage("加入失敗，已是會員，若忘記密碼，請聯絡管理員");
        }
        return store;
    }

    @Override
    public Page<Store> searchRegisterStore(Store store, Integer page) {
        //分頁與排序
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "registerDay"));

        // 回傳的店家列表
        List<RegisterStoreDTO> storeList = new ArrayList<>();

        // 搜尋店家狀態
        List<Integer> storeStatus  = new ArrayList<>();
        if(store.getStoreStatus() == 10){           // 店家狀態 : 審核中 & 拒絕
            storeStatus.add(0);    //狀態 : 審核中
            storeStatus.add(4);    //狀態 : 拒絕
        }else {
            storeStatus.add(store.getStoreStatus());
        }

        //只依店家狀態搜尋
        if(store.getVat() == null && store.getStoreName() == null){
            return storeDao.findByStoreStatusIn(storeStatus, pageable);
        }

        //依店家狀態 & 統編 & 店家名稱搜尋
        if(store.getVat() != null && store.getStoreName() != null){
            return storeDao.findByStoreStatusInAndVatContainingAndStoreNameContaining(storeStatus, store.getVat(), store.getStoreName(), pageable);
        }

        //依店家狀態 & (統編 OR 店家名稱)
        if (store.getVat() == null){
            store.setVat("********");
        }
        if(store.getStoreName() == null){
            store.setStoreName("********");
        }
        return storeDao.findByStoreStatusInAndVatOrStoreNameContaining(storeStatus, store.getVat(), store.getStoreName(), pageable);

    }

    @Override
    public RegisterStoreDTO searchRegisterStoreDetail(Store store) {
        Store result = storeDao.findByStoreId(store.getStoreId());
        return StoreToRegisterStore.convertToRegisterStore(result);
    }

}
