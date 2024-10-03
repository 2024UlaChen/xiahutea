package idv.tia201.g2.web.store.service;

import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.vo.CustomerLoyaltyCard;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface StoreService {
    //列出全部
    List<Store> findAll();
    List<Store> GetStoreList();
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
    Store editStoreStatus(Integer storeId);
    byte[] findLogoById(Integer id);
    void editLogoByStoreId(MultipartFile file, Integer storeId) throws IOException;

    void addStoreHolidayByDate(Integer storeId, Date holiday);

    List<Store> getStoreListWorking(String holiday) throws ParseException;
    List<Store> getAllData();
    List<Store> getAllStoreById(Integer Id);
    TotalUserDTO GetTotalUser(Integer StoreId);
    List<Date> GetStoreHolidays(Integer StoreId);

    Page<Store> searchStore(StoreViewModel store, Integer page);
    List<Store> getStoreListForHome(Date today) throws ParseException;

    Store updateStoreRank(Integer storeId,Float score);
    CustomerLoyaltyCard updateMemberStoreLoyaltyPoints(Integer storeId, Integer memberId, Integer totalMoney);

}
