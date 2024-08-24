package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreDao extends JpaRepository<Store, Integer> {
    List<Store> findByStoreName(String storeName);
    @Query("FROM Store WHERE storeAddress LIKE %:area")
    List<Store> findByStoreAddress(@Param("area") String storeAddress);

}
