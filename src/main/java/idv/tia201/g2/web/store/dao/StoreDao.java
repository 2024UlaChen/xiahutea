package idv.tia201.g2.web.store.dao;

import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreDao extends JpaRepository<Store, Integer> {
    Store save(Store store);
    Optional<Store> findById(Integer id);
    List<Store> findByName(String name);

}
