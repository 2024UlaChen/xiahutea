package idv.tia201.g2.web.store.util;

import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class StoreToRegisterStore {
//   將單一類別進行替換
    public static RegisterStoreDTO convertToRegisterStore(Store store) {
        RegisterStoreDTO registerStore = new RegisterStoreDTO();

        BeanUtils.copyProperties(store, registerStore);
        return registerStore;
    }

//    將整個Page類別的內容進行替換
    public static Page<RegisterStoreDTO> convertPage(Page<Store> storePage) {
        List<RegisterStoreDTO> registerStoreList = storePage.getContent()
                .stream()
                .map(StoreToRegisterStore::convertToRegisterStore)
                .toList();

        return new PageImpl<>(registerStoreList, storePage.getPageable(), storePage.getTotalElements());
    }
}
