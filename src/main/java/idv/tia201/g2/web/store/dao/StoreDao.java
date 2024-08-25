package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StoreDao extends JpaRepository<Store, Integer> {

    //List<Store> findByStoreName(String storeName);

    //根據店名進行模糊搜尋
//    @Query("FROM Store s WHERE s.storeName LIKE %:name%")
//    List<Store> findByStoreNameLike(@Param("name") String storeName);

    //根據區域選擇商家 使用JPQL語法 查詢的是Entity
//    @Query("FROM Store WHERE storeAddress LIKE %:area%")
//    List<Store> findByStoreAddress(@Param("area") String storeAddress);
//    @Query("FROM Store WHERE storeName LIKE %:name% OR storeAddress LIKE %:area ")
//    List<Store> findByStoreNameOrStoreAddress(@Param("name") String name, @Param("area")String address);
// 以上都有更簡潔的生成查詢
    //模糊查詢店名  並且大小寫不敏感
    List<Store> findByStoreNameContainingIgnoreCase(String name);
    //區域模糊查詢
    List<Store> findByStoreAddressContaining(String address);
    //根據區域 和店名 模糊查詢
    List<Store> findByStoreNameContainingOrStoreAddressContaining(String name, String address);

}
