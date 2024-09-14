package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StoreDao extends JpaRepository<Store, Integer> {

    Store findByStoreId(Integer storeId);
    //模糊查詢店名  並且大小寫不敏感
    List<Store> findByStoreNameContainingIgnoreCase(String name);
    //區域模糊查詢
    List<Store> findByStoreAddressContaining(String address);
    //根據區域 和店名 模糊查詢
    List<Store> findByStoreNameContainingOrStoreAddressContaining(String name, String address);
    //登入用
    Store findByVat(String vat);

    // 依店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusIn(List<Integer> statuses, Pageable pageable);

    // 依統編 & 店家名稱 & 店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusInAndVatContainingAndStoreNameContaining(
            List<Integer> storeStatus, String vat, String storeName, Pageable pageable);

    // 依統編 or 店家名稱 & 店家狀態搜尋(可複選)
    Page<Store> findByStoreStatusInAndVatOrStoreNameContaining(
            List<Integer> storeStatus, String vat, String storeName, Pageable pageable);
}
