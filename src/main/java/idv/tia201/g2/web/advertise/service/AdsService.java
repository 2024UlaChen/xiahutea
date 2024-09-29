package idv.tia201.g2.web.advertise.service;

import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.vo.TotalUsers;

import java.util.List;

public interface AdsService {
    public Advertise saveAdvertise(Advertise ad);
    public List<Advertise> findAllAdvertises();
    public TotalUsers getTotalUsers(long userid);
    public Advertise findAdvertiseById(int id);
    public List<Store> findByids(List<Integer> userids);
    public List<Advertise> findstoreAdvertises(Long userid);
    public Store findStoreById(int id);
}
