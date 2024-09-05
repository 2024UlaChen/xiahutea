package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;
    //注入dao
    @Autowired
    public StoreServiceImpl(StoreDao storeDao) {
        this.storeDao = storeDao;
    }


    public List<StoreViewModel> GetStoreViewModels() {

        List<Store> list = findAll();
        List<StoreViewModel> storeViewModels = new ArrayList<>();
        StoreViewModel storeViewModel = new StoreViewModel(); ;
        for(Store store : list) {
            BeanUtils.copyProperties(store, storeViewModel);
            storeViewModel.setStoreId(store.getStoreId());
            storeViewModel.setStoreName(store.getStoreName());
            storeViewModel.setRegisterDay(store.getRegisterDay());
            storeViewModel.setVat(store.getVat());
            storeViewModel.setContactPhone(store.getContactPhone());
            storeViewModel.setStoreStatus(store.getStoreStatus());

            storeViewModels.add(storeViewModel);
        }
        return storeViewModels;
    }

    @Override
    public Store loginStore(Store userData) {
        if(userData.getVat() == null || userData.getPassword() ==null) {return null;}
        Store data = storeDao.findByVat(userData.getVat());
        if( data == null  ) {return null;}
        return data.getPassword().equals(userData.getPassword())? data:null;
    }


    @Override
    public List<Store> findAll() {
        List<Store> list = storeDao.findAll();
        for(Store store : list) {
            store.setBankCode(null);
            store.setBankAccount(null);
            store.setStoreAddress(null);
            store.setStorePhone(null);
            store.setOpeningHours(null);
            store.setClosingHours(null);
            store.setIsDelivery(null);
            store.setDeliveryDistance(null);
            store.setDeliveryMoney(null);
            store.setIsTakeOrders(null);
            store.setScore(null);
            store.setIsCash(null);
            store.setIsCreditCard(null);
            store.setLogo(null);
            store.setEmail(null);
            store.setPassword(null);
            store.setOwner(null);

        }
        return storeDao.findAll();
    }

    @Override
    public List<Store> findAll(Pageable pageable) {

        return storeDao.findAll(pageable).getContent();
    }

    @Override
    public List<Store> findStoreByName(String name) {
        //模糊查詢並且大小寫不敏感
        return storeDao.findByStoreNameContainingIgnoreCase(name);
    }

    @Override
    public Store findStoreById(Integer id) {
        //避免空指標例外  不該觸發
        return storeDao.findById(id).orElse(null);
    }

    @Override
    public List<Store> findStoreByAddress(String address) {
        //模糊區域查詢
        return storeDao.findByStoreAddressContaining(address);
    }

    @Override
    public Store saveStore(Store store) {
        //jpa 把新增編輯統合 如果id為空就會執行新增 有id就會編輯
        //填寫招商 要執行新增 發送註冊信要引導成編輯
        return storeDao.save(store);
    }

    @Override
    public Store editStorePassword(Store store){
        //預計只回傳回PK和密碼  修改密碼
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setPassword(store.getPassword());
        return storeDao.save(oldDate);
    }



    @Override
    public Store editStoreInfo(Store store) {
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setStorePhone(store.getStorePhone());
        oldDate.setDeliveryMoney(store.getDeliveryMoney());
        oldDate.setContactPhone(store.getContactPhone());
        oldDate.setContactPerson(store.getContactPerson());
        oldDate.setOpeningHours(store.getOpeningHours());
        oldDate.setClosingHours(store.getClosingHours());
//        ToDo 休假日 和 令張表關聯
        oldDate.setIsDelivery(store.getIsDelivery());
        oldDate.setDeliveryDistance(store.getDeliveryDistance());
        oldDate.setIsTakeOrders(store.getIsTakeOrders());
        oldDate.setIsCash(store.getIsCash());
        oldDate.setIsCreditCard(store.getIsCreditCard());
        oldDate.setLogo(store.getLogo());
        oldDate.setEmail(store.getEmail());
        return storeDao.save(oldDate);
    }

    @Override
    public Store editStoreLoyaltyCard(Store store) {
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setLoyaltyCardName(store.getLoyaltyCardName());
        oldDate.setExchangeRate(store.getExchangeRate());
        oldDate.setValidStatus(store.getValidStatus());
        if(store.getValidStatus()){
            oldDate.setExpiredDate(null);
        }else{
            //當下時間加半年
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.MONTH, 6);
            now = new Timestamp(cal.getTimeInMillis());
            oldDate.setExpiredDate(now);
        }
        return storeDao.save(oldDate);
    }

}
