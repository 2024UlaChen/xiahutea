package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<StoreViewModel> storeViewModels = null ;
        StoreViewModel storeViewModel = null ;
        for(Store store : list) {
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
    public Boolean loginStore(Store userData) {
        if(userData.getVat() == null || userData.getPassword() ==null) {return false;}
        Store data = storeDao.findByVat(userData.getVat());
        if( data == null  ) {return false;}
        return data.getPassword().equals(userData.getPassword());
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

}
