package idv.tia201.g2.web.store.service;

import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface StoreService {
    //列出全部
    List<Store> findAll();
    List<Store> findAll(Pageable pageable);
    //搜尋店家 只要特定字
    List<Store> findStoreByName(String name);
    List<StoreViewModel> GetStoreViewModels();
    Store loginStore(Store data);
    //用於查修 準確指定
    Store findStoreById(Integer id);

    List<Store> findStoreByAddress(String address);
    Store saveStore(Store store);
    Store editStorePassword(Store store);
    Store editStoreInfo(Store store);
    Store editStoreLoyaltyCard(Store store);
    byte[] findLogoById(Integer id);
    Store editLogoByStoreId(MultipartFile file, Integer storeId) throws IOException;

    void addStoreHolidayByDate(Store store, Date holiday);

    List<Store> getStoreListNoWorking(Date holiday);
    List<Store> getAllData();
    List<Store> getAllStoreById();
}
