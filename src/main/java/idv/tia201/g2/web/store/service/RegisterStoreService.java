package idv.tia201.g2.web.store.service;

import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.data.domain.Page;

public interface RegisterStoreService {
    public Store register(Store store);
    public Page<Store> searchRegisterStore(Store store, Integer page);

    RegisterStoreDTO searchRegisterStoreDetail(Store store);
}
