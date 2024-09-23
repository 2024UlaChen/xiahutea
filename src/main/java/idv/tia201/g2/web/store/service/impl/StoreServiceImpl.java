package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.web.store.dao.StoreCalendarRepository;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.model.StoreViewModel;
import idv.tia201.g2.web.store.service.StoreService;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.store.vo.StoreCalendar;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao;
    private final StoreCalendarRepository storeCalendarRepository;
    private final TotalUserDao totalUserDao;
    //注入dao
    @Autowired
    public StoreServiceImpl(StoreDao storeDao, StoreCalendarRepository storeCalendarRepository,TotalUserDao totalUserDao) {
        this.storeDao = storeDao;
        this.storeCalendarRepository = storeCalendarRepository;
        this.totalUserDao = totalUserDao;
    }


    public List<StoreViewModel> GetStoreViewModels() {

        List<Store> list = findAll();


        List<StoreViewModel> storeViewModels = new ArrayList<>();


        StoreViewModel storeViewModel = new StoreViewModel();

        for(Store store : list) {
            BeanUtils.copyProperties(store, storeViewModel);
            storeViewModel.setStoreId(store.getStoreId());
            storeViewModel.setStoreName(store.getStoreName());
            storeViewModel.setRegisterDay(store.getRegisterDay());
            storeViewModel.setVat(store.getVat());
            storeViewModel.setContactPhone(store.getContactPhone());
            storeViewModel.setStoreStatus(store.getStoreStatus());
            storeViewModels.add(storeViewModel);
        }
        return storeViewModels;
    }

    @Override
    public Store loginStore(Store userData) {

        if(userData.getVat() == null || userData.getPassword() ==null) {return null;}
        Store data = storeDao.findByVat(userData.getVat());
        if( data == null  ) {return null;}
        if(data.getPassword().equals(userData.getPassword())){
            //密碼正確 登入成功
            data.setMessage("登入成功");
            data.setSuccessful(true);
            return data;
        }
        return null;
    }


    @Override
    public List<Store> findAll() {
        List<Store> list = storeDao.findAll();
        for(Store store : list) {
            store.setBankCode(null);
            store.setBankAccount(null);
            store.setStoreAddress(null);
            store.setStorePhone(null);
            store.setOpeningHours(null);
            store.setClosingHours(null);
            store.setIsDelivery(null);
            store.setDeliveryDistance(null);
            store.setDeliveryMoney(null);
            store.setIsTakeOrders(null);
            store.setScore(null);
            store.setLogo(null);
            store.setEmail(null);
            store.setPassword(null);
            store.setOwner(null);

        }
        return storeDao.findAll();
    }

    @Override
    public List<Store> GetStoreList() {
        return storeDao.findByStoreStatus();
    }

    @Override
    public List<Store> findAll(Pageable pageable) {

        return storeDao.findAll(pageable).getContent();
    }

    @Override
    public List<Store> findStoreByName(String name) {
        //模糊查詢並且大小寫不敏感
        return storeDao.findByStoreNameContainingIgnoreCase(name);
    }

    @Override
    public Store findStoreById(Integer id) {
        //避免空指標例外  不該觸發
        Store data = storeDao.findById(id).orElse(null);
        if(data == null) {return null;}
        data.setSuccessful(true);
        return data;
    }

    @Override
    public List<Store> findStoreByAddress(String address) {
        //模糊區域查詢
        return storeDao.findByStoreAddressContaining(address);
    }

    @Override
    public Store saveStore(Store store) {
        //jpa 把新增編輯統合 如果id為空就會執行新增 有id就會編輯
        //填寫招商 要執行新增 發送註冊信要引導成編輯
        return storeDao.save(store);
    }

    @Override
    public Store editStorePassword(Store store){
        //預計只回傳回PK和密碼  修改密碼
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setPassword(store.getPassword());
        return storeDao.save(oldDate);
    }



    @Override
    public Store editStoreInfo(Store store) {
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setStorePhone(store.getStorePhone());
        oldDate.setDeliveryMoney(store.getDeliveryMoney());
        oldDate.setContactPhone(store.getContactPhone());
        oldDate.setContactPerson(store.getContactPerson());
        oldDate.setOpeningHours(store.getOpeningHours());
        oldDate.setClosingHours(store.getClosingHours());
//        ToDo 休假日 和 令張表關聯
        oldDate.setIsDelivery(store.getIsDelivery());
        oldDate.setDeliveryDistance(store.getDeliveryDistance());
        oldDate.setIsTakeOrders(store.getIsTakeOrders());

        oldDate.setEmail(store.getEmail());

        return storeDao.save(oldDate);
    }

    @Override
    public Store editStoreLoyaltyCard(Store store) {
        Store oldDate = findStoreById(store.getStoreId());
        oldDate.setLoyaltyCardName(store.getLoyaltyCardName());

        oldDate.setExchangeRate(store.getExchangeRate());
        oldDate.setValidStatus(store.getValidStatus());
        if(store.getValidStatus()){
            oldDate.setExpiredDate(null);
            //激活中 但未設定兌換比例 預設100元一點
            if(oldDate.getExchangeRate() == null){
                oldDate.setExchangeRate(100);
            }
        }else{
            //當下時間加半年
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.MONTH, 6);
            now = new Timestamp(cal.getTimeInMillis());
            oldDate.setExpiredDate(now);
        }
        return storeDao.save(oldDate);
    }

    @Override
    public Store editStoreStatus(Integer storeId) {
        Store data = findStoreById(storeId);
        if(data.getStoreStatus() == 1){
            data.setStoreStatus(2);
            return storeDao.save(data);
        }
        if(data.getStoreStatus() == 2){
            data.setStoreStatus(1);
            return storeDao.save(data);
        }
        return null;
    }

    @Override
    public byte[] findLogoById(Integer id) {
        Store store = findStoreById(id);
        return store.getLogo();



    }

    @Override
    public void editLogoByStoreId(MultipartFile file, Integer storeId) throws IOException {
        Store store = findStoreById(storeId);
        store.setLogo(file.getBytes());
        storeDao.save(store);



    }

    @Override
    public void addStoreHolidayByDate(Integer storeId, Date holiday) {
        StoreCalendar data = new StoreCalendar();


        data.setStoreId(storeId);
        data.setStoreHoliday(holiday);
        storeCalendarRepository.save(data);

    }

    @Override
    public List<Store> getStoreListWorking(String dateStr) throws ParseException {
        //取得上班店家

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateStr);

        List<Store> AllStore = storeDao.findAll();
        List<Store> NoWorkingList = storeCalendarRepository.findByStoreHoliday(date);
        List<Integer> allIdList = new ArrayList<>();
        for(Store store : AllStore) {
            allIdList.add(store.getStoreId());
        }

        List<Integer> NoWorkingIdlist = new ArrayList<>();
        for(Store store : NoWorkingList) {
            Integer id = store.getStoreId();
            if(!NoWorkingIdlist.contains(id)) {
                NoWorkingIdlist.add(id);
            }
        }
        allIdList.removeAll(NoWorkingIdlist); //取得上班店家Id清單
        List<Store> WorkingList = new ArrayList<>();
        for(Integer id : allIdList) {
            WorkingList.add(findStoreById(id));
        }



        return WorkingList;

    }
    public List<Store> getAllData(){
        return storeCalendarRepository.findAllByStore();
    }
    public List<Store> getAllStoreById(Integer Id){
        return storeCalendarRepository.findStoreByStoreId(Id);
    }

    @Override
    public TotalUserDTO GetTotalUserDTO(Integer StoreId) {
        TotalUserDTO item = new TotalUserDTO();
        Store store = findStoreById(StoreId);
        TotalUsers totalUsers = totalUserDao.findByUserTypeIdAndUserId(1,StoreId);
        item.setTotalUserId(totalUsers.getTotalUserId());
        item.setUserTypeId(totalUsers.getUserTypeId());
        item.setUserId(store.getStoreId());
        item.setLogo(store.getLogo());
        return item;

    }

    @Override
    public List<Date> GetStoreHolidays(Integer StoreId) {
        return storeCalendarRepository.findStoreCalendarsByStoreId(StoreId);
    }

    @Override
    public Page<Store> searchStore(StoreViewModel store, Integer page) {
        //分頁與排序
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "registerDay"));
        //回傳的列表
        List<Store> storeList = new ArrayList<>();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(store.getStoreName()==null && store.getVat() == null && store.getSearcherStart() == null && store.getSearcherEnd() == null){
            //四大皆空 只會顯示 已申請和停權
            return storeDao.findByStoreStatus(pageable);

        } else if (store.getStoreName() == null && store.getVat() == null  ) {

            if(store.getSearcherEnd() == null){
                
                return storeDao.findByRegisterDayBetween(store.getSearcherStart(),now,pageable);

            } else if (store.getSearcherStart() == null) {
                
                return storeDao.findByRegisterDayBetween(now,store.getSearcherEnd(),pageable);
            }

            return storeDao.findByRegisterDayBetween(store.getSearcherStart(),store.getSearcherEnd(),pageable);
        } else if (store.getStoreName() == null) {
            if(store.getSearcherStart() == null && store.getSearcherEnd() == null){
                return storeDao.findByVatContaining(store.getVat(),pageable);
            } else if (store.getSearcherEnd() == null){

                return storeDao.findByVatContainingAndRegisterDayBetween(store.getVat(),store.getSearcherStart(),now,pageable);

            } else if (store.getSearcherStart() == null) {

                return storeDao.findByVatContainingAndRegisterDayBetween(store.getVat(),now,store.getSearcherEnd(),pageable);
            }
            return storeDao.findByVatContainingAndRegisterDayBetween(store.getVat(),store.getSearcherStart(),store.getSearcherEnd(),pageable);


        } else if (store.getVat() == null) {
            if(store.getSearcherStart() == null && store.getSearcherEnd() == null){
                return storeDao.findByStoreNameContainingIgnoreCase(store.getStoreName(),pageable);
            } else if (store.getSearcherEnd() == null){

                return storeDao.findByStoreNameContainingIgnoreCaseAndRegisterDayBetween(store.getStoreName(),store.getSearcherStart(),now,pageable);

            } else if (store.getSearcherStart() == null) {

                return storeDao.findByStoreNameContainingIgnoreCaseAndRegisterDayBetween(store.getStoreName(),now,store.getSearcherEnd(),pageable);
            }
            return storeDao.findByStoreNameContainingIgnoreCaseAndRegisterDayBetween(store.getStoreName(),store.getSearcherStart(),store.getSearcherEnd(),pageable);

        }
        return storeDao.findByStoreNameContainingIgnoreCaseAndVatContainingAndRegisterDayBetween(store.getStoreName(),store.getVat(),store.getSearcherStart(),store.getSearcherEnd(),pageable);

    }


}
