package idv.tia201.g2.web.advertise.service.Impl;

import idv.tia201.g2.core.pojo.Core;
import idv.tia201.g2.web.advertise.dao.AdsDao;
import idv.tia201.g2.web.advertise.service.AdsService;
import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.coupon.vo.Coupon;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AdsServiceImpl implements AdsService {
    @Autowired
    private AdsDao adsDao;
    @Autowired
    private TotalUserDao totalUserDao;
    @Autowired
    private StoreDao storeDao;

    @Override
    public Advertise saveAdvertise(Advertise ad) {
        if (ad.getTitle() == null) {
            ad.setMessage("廣告標題未輸入");
            ad.setSuccessful(false);
            return ad;
        }
        if (ad.getDescription() == null) {
            if (adsDao.findBytitle(ad.getTitle()) != null) {
                ad.setMessage("廣告標題重複");
                ad.setSuccessful(false);
                return ad;
            }
        } else {
            // 編輯情形：檢查廣告標題重複是否重複，但排除當前編輯的廣告
            Advertise existingads = adsDao.findBytitle(ad.getTitle());
            if (existingads != null && !existingads.getAdsId().equals(ad.getAdsId())) {
                ad.setMessage("廣告標題重複");
                ad.setSuccessful(false);
                return ad;
            }
        }
        if (ad.getDescription() == null) {
            ad.setMessage("廣告內容未輸入");
            ad.setSuccessful(false);
            return ad;
        }
        if (ad.getStartTime() == null || ad.getEndTime() == null) {
            ad.setMessage("廣告未設定期限");
            ad.setSuccessful(false);
            return ad;
        }
        if (ad.getEndTime().before(ad.getStartTime())) {
            ad.setMessage("廣告結束日期不能小於開始日期");
            ad.setSuccessful(false);
            return ad;
        }
        // 檢查圖片是否為空
        if (ad.getImgUrl() == null || ad.getImgUrl().length == 0) {
            ad.setMessage("廣告圖片未上傳");
            ad.setSuccessful(false);
            return ad;
        }
        ad = adsDao.save(ad);
        ad.setSuccessful(true);
        return ad;
    }

    @Override
    public List<Advertise> findAllAdvertises() {
        return adsDao.findAll();
    }

    @Override
    public TotalUsers getTotalUsers(long userId) {
        return totalUserDao.findByTotalUserId(userId);
    }

    @Override
    public Advertise findAdvertiseById(int adid) {
        return adsDao.findById(adid)
                .orElse(new Advertise());
    }

    @Override
    public List<Store> findByids(List<Integer> userids) {
        return storeDao.findAllById(userids);
    }

    @Override
    public List<Advertise> findstoreAdvertises(Long userid) {
        return adsDao.findByAdsTotalUserid(userid);
    }

    @Override
    public Store findStoreById(int storeid) {
        return storeDao.findByStoreId(storeid);
    }

    @Override
    public List<Advertise> getHomePageAdvertises() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return adsDao.findByHomeDisplayAndIsactiveAndStartTimeLessThanAndEndTimeGreaterThan(true,true,now,now);
    }
}