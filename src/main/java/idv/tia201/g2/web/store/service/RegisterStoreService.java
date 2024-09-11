package idv.tia201.g2.web.store.service;

import idv.tia201.g2.web.member.vo.Member;
import idv.tia201.g2.web.store.vo.Store;

import java.util.List;

public interface RegisterStoreService {
    public Store register(Store store);
    public List<Store> searchRegisterStore(Store store);

}
