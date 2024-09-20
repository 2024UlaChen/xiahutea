package idv.tia201.g2.web.store.service;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.vo.Store;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface RegisterStoreService {
    Store register(Store store);
    Page<Store> searchRegisterStore(Store store, Integer page);
    RegisterStoreDTO searchRegisterStoreDetail(Store store);
    Store editRegisterStore(Store newData) throws MessagingException, IOException;
    void sendMail(Store save) throws MessagingException, IOException;
}
