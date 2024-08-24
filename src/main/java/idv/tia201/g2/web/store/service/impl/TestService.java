package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//由spring管理 要使用這個bean只要注入即可
public class TestService {
    private StoreDao dao;
    @Autowired
    public TestService(StoreDao dao) {
        this.dao = dao;
    }
    public Store save(Store store) {
        return dao.save(store);
    }
    //玩玩bll
    public String hi(){
        return "hi";
    }
}
