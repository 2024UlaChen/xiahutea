package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
@Repository
public interface StoreDao extends JpaRepository<Store, Integer> {

    Store findByStoreId(Integer storeId);
    //模糊查詢店名  並且大小寫不敏感 並且只回傳使用中
    List<Store> findByStoreNameContainingIgnoreCaseAndStoreStatus(String name,Integer status);
    @Query("SELECT s FROM Store s LEFT JOIN StoreCalendar sc ON s.storeId = sc.storeId LEFT JOIN Product p ON s.storeId = p.productStoreId WHERE (s.storeStatus = 1 AND sc.storeHoliday != current date) AND (s.storeName LIKE %:name% OR p.productName LIKE %:name%)")
    List<Store> SearchBarData(String name);
    //統編模糊查詢
    List<Store> findByVatContaining(String vat);
    //統編與店名模糊查詢
    List<Store> findByStoreNameContainingIgnoreCaseAndVatContaining(String storeName, String vat);
    @Query("FROM Store s WHERE s.storeStatus = 1 OR s.storeStatus = 2")
    List<Store> findByStoreStatus();
    List<Store> findByStoreStatus(Integer storeStatus);

    //區域模糊查詢
    List<Store> findByStoreAddressContaining(String address);
    List<Store> findByStoreAddressContainingAndStoreStatus(String address, Integer storeStatus);
    //根據區域 和店名 模糊查詢
    List<Store> findByStoreNameContainingOrStoreAddressContaining(String name, String address);
    //登入用
    Store findByVat(String vat);




    // 依店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusIn(List<Integer> statuses, Pageable pageable);

    // 依統編 & 店家名稱 & 店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusInAndVatContainingAndStoreNameContaining(
            List<Integer> storeStatus, String vat, String storeName, Pageable pageable);

    @Query("FROM Store s WHERE s.storeStatus = 1 OR s.storeStatus = 2")
    Page<Store> findByStoreStatus(Pageable pageable);


    Page<Store> findByRegisterDayBetween(Timestamp startDate, Timestamp endDate, Pageable pageable);
    Page<Store> findByStoreNameContainingIgnoreCaseAndVatContainingAndRegisterDayBetween(String storeName,String vat,Timestamp startDate, Timestamp endDate, Pageable pageable);
    Page<Store> findByVatContainingAndRegisterDayBetween(String vat,Timestamp startDate, Timestamp endDate, Pageable pageable);
    Page<Store> findByStoreNameContainingIgnoreCaseAndRegisterDayBetween(String storeName,Timestamp startDate, Timestamp endDate, Pageable pageable);
    // 依統編 or 店家名稱 & 店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusInAndVatOrStoreNameContaining(
            List<Integer> storeStatus, String vat, String storeName, Pageable pageable);
    Page<Store> findByVatContaining(String vat, Pageable pageable);
    Page<Store> findByStoreNameContaining(String name, Pageable pageable);
    Page<Store> findByStoreNameContainingAndVatContaining(String storeName, String vat, Pageable pageable);
    Page<Store> findByStoreStatus(Integer storeStatus, Pageable pageable);

    //依照註冊時間區間 以及帳號狀態
    Page<Store> findByRegisterDayBetweenAndStoreStatusIn(Timestamp startDate, Timestamp endDate,List<Integer> statuslist, Pageable pageable);
    //依照註冊name,vat 註冊時間 以及帳號狀態
    Page<Store> findByStoreNameContainingAndVatContainingAndRegisterDayBetweenAndStoreStatusIn(String storeName,String vat,Timestamp startDate, Timestamp endDate,List<Integer> statuslist, Pageable pageable);
    //依照註冊vat 註冊時間 以及帳號狀態
    Page<Store> findByVatContainingAndRegisterDayBetweenAndStoreStatusIn(String vat,Timestamp startDate, Timestamp endDate,List<Integer> statuslist ,Pageable pageable);
    //依照註冊name 註冊時間 以及帳號狀態
    Page<Store> findByStoreNameContainingAndRegisterDayBetweenAndStoreStatusIn(String storeName,Timestamp startDate, Timestamp endDate,List<Integer> statuslist ,Pageable pageable);
    //依照 vat  以及帳號狀態
    Page<Store> findByVatContainingAndStoreStatusIn(String vat,List<Integer> statuslist, Pageable pageable);
    //依照 name 以及 帳號狀態
    Page<Store> findByStoreNameContainingAndStoreStatusIn(String name,List<Integer> statuslist, Pageable pageable);
    // name vat status
    Page<Store> findByStoreNameContainingIgnoreCaseAndVatContainingAndStoreStatusIn(String name,String vat,List<Integer> statuslist, Pageable pageable);

}
