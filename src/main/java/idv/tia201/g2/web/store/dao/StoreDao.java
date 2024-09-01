package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
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
}
