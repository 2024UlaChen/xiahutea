package idv.tia201.g2.web.store.service.impl;

import idv.tia201.g2.core.pojo.Mail;
import idv.tia201.g2.core.util.EncrypSHA;
import idv.tia201.g2.core.util.MailUtil;
import idv.tia201.g2.web.chat.service.ChatRoomService;
import idv.tia201.g2.web.store.dao.StoreDao;
import idv.tia201.g2.web.store.dto.RegisterStoreDTO;
import idv.tia201.g2.web.store.service.RegisterStoreService;
import idv.tia201.g2.web.store.util.StoreToRegisterStore;
import idv.tia201.g2.web.store.vo.Store;
import idv.tia201.g2.web.user.dao.TotalUserDao;
import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.TotalUsers;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static idv.tia201.g2.core.util.CopyUtil.copyPropertiesIgnoreNull;
import static idv.tia201.g2.core.util.ValidateUtil.isValidTWBID;
import static idv.tia201.g2.web.store.util.PasswordUtil.generateRandomString;

@Service
public class RegisterStoreServiceImpl implements RegisterStoreService {
    @Autowired
    private StoreDao storeDao;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private TotalUserDao totalUserDao;

    Integer userType = 1;
    @Autowired
    private ChatRoomService chatRoomService;

    @Override
    public Store register(Store store) {
        //必填欄位
        if (store.getContactPerson().isEmpty()) {
            store.setMessage("請輸入聯絡人資料");
            return store;
        }
        if (store.getEmail().isEmpty()) {
            store.setMessage("請輸入E-mail");
            return store;
        }
        if (store.getContactPhone().isEmpty()) {
            store.setMessage("請輸入聯絡人電話");
            return store;
        }
        if (store.getStoreName().isEmpty()) {
            store.setMessage("請輸入店家名稱");
            return store;
        }
        if (store.getStoreAddress().isEmpty()) {
            store.setMessage("請輸入店家地址");
            return store;
        }
        if (store.getOwner().isEmpty()) {
            store.setMessage("請輸入店家負責人");
            return store;
        }
        if (store.getVat().isEmpty()) {
            store.setMessage("請輸入統一編號");
            return store;
        }

        //電話驗證
        String phonePattern = "(\\d{2,3}-?|\\(\\d{2,3}\\))\\d{3,4}-?\\d{4}|09\\d{2}(\\d{6}|-\\d{3}-\\d{3})";
        boolean phoneMatcher = Pattern.matches(phonePattern, store.getContactPhone());
        if (!phoneMatcher) {
            store.setMessage("聯絡電話錯誤");
            return store;
        }

        //統編驗證
        if(!isValidTWBID(store.getVat().trim())){
            store.setMessage("統一編號錯誤");
            return store;
        }

        //E-mail驗證
        String emailPattern = "^(.+)@(.+)$";
        boolean emailMatcher = Pattern.matches(emailPattern, store.getEmail());
        if (!emailMatcher) {
            store.setMessage("信箱錯誤");
            return store;
        }

        //依照統一編號查詢是否已是會員
        Store oldStore = storeDao.findByVat(store.getVat());
        if(oldStore == null){
            store.setStoreStatus(0);//狀態預設為0
            Timestamp registerDay = new Timestamp(System.currentTimeMillis());
            store.setRegisterDay(registerDay);
            store.setScore(0f);
            storeDao.save(store);
            store.setSuccessful(true);
            store.setMessage("成功加入會員");
        }else {
            store.setMessage("加入失敗，已是會員，若忘記密碼，請聯絡管理員");
        }
        return store;
    }

    @Override
    public Page<Store> searchRegisterStore(Store store, Integer page) {
        //分頁與排序
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "registerDay"));

        // 回傳的店家列表
        List<RegisterStoreDTO> storeList = new ArrayList<>();

        // 搜尋店家狀態
        List<Integer> storeStatus  = new ArrayList<>();
        if(store.getStoreStatus() == 10){           // 店家狀態 : 審核中 & 拒絕
            storeStatus.add(0);    //狀態 : 審核中
            storeStatus.add(4);    //狀態 : 拒絕
        }else {
            storeStatus.add(store.getStoreStatus());
        }

        //只依店家狀態搜尋
        if(store.getVat() == null && store.getStoreName() == null){
            return storeDao.findByStoreStatusIn(storeStatus, pageable);
        }

        //依店家狀態 & 統編 & 店家名稱搜尋
        if(store.getVat() != null && store.getStoreName() != null){
            return storeDao.findByStoreStatusInAndVatContainingAndStoreNameContaining(storeStatus, store.getVat(), store.getStoreName(), pageable);
        }

        //依店家狀態 & (統編 OR 店家名稱)
        if (store.getVat() == null){
            store.setVat("********");
        }
        if(store.getStoreName() == null){
            store.setStoreName("********");
        }
        return storeDao.findByStoreStatusInAndVatOrStoreNameContaining(storeStatus, store.getVat(), store.getStoreName(), pageable);

    }

    @Override
    public RegisterStoreDTO searchRegisterStoreDetail(Store store) {
        Store result = storeDao.findByStoreId(store.getStoreId());
        return StoreToRegisterStore.convertToRegisterStore(result);
    }

    public Store editRegisterStore(Store newData) throws MessagingException, IOException {
        //必填欄位
        if (newData.getContactPerson() == null || newData.getContactPerson().isEmpty()) {
            newData.setMessage("請輸入聯絡人資料");
            return newData;
        }
        if (newData.getEmail() == null || newData.getEmail().isEmpty()) {
            newData.setMessage("請輸入E-mail");
            return newData;
        }
        if (newData.getContactPhone() == null || newData.getContactPhone().isEmpty()) {
            newData.setMessage("請輸入聯絡人電話");
            return newData;
        }
        if (newData.getStoreName() == null || newData.getStoreName().isEmpty()) {
            newData.setMessage("請輸入店家名稱");
            return newData;
        }
        if (newData.getStoreAddress() == null || newData.getStoreAddress().isEmpty()) {
            newData.setMessage("請輸入店家地址");
            return newData;
        }
        if (newData.getOwner() == null || newData.getOwner().isEmpty()) {
            newData.setMessage("請輸入店家負責人");
            return newData;
        }


        //電話驗證
        String phonePattern = "(\\d{2,3}-?|\\(\\d{2,3}\\))\\d{3,4}-?\\d{4}|09\\d{2}(\\d{6}|-\\d{3}-\\d{3})";
        boolean phoneMatcher = Pattern.matches(phonePattern, newData.getContactPhone());
        if (!phoneMatcher) {
            newData.setMessage("聯絡電話錯誤");
            return newData;
        }

        //E-mail驗證
        String emailPattern = "^(.+)@(.+)$";
        boolean emailMatcher = Pattern.matches(emailPattern, newData.getEmail());
        if (!emailMatcher) {
            newData.setMessage("信箱錯誤");
            return newData;
        }

//        若狀態為「成為店家」 就要 產生密碼 & 發信 & 成為totalUser & 新增聊天室
        if (newData.getStoreStatus() == 1) {
            //新增亂數密碼
            String randomPassword = generateRandomString(9, 14);
            newData.setPassword(randomPassword);
            //寄信
            sendMail(newData);
            String shaedEncrypt = EncrypSHA.SHAEncrypt(randomPassword);
            newData.setPassword(shaedEncrypt);

            //評分為0
            newData.setScore(0f);


            TotalUsers totalUser = new TotalUsers(null, userType, newData.getStoreId());
            TotalUsers save = totalUserDao.save(totalUser);

            TotalUserDTO totalUserDTO = new TotalUserDTO();
            BeanUtils.copyProperties(save,totalUserDTO);

            chatRoomService.addChatRoom(totalUserDTO);

        }

        //依 userId 存資料
        Store store = storeDao.findByStoreId(newData.getStoreId());
        copyPropertiesIgnoreNull(newData,store);
        Store save = storeDao.save(store);

        save.setSuccessful(true);
        save.setMessage("更新成功");
        return save;
    }

    public void sendMail(Store newData) throws MessagingException, IOException {
        Mail mail = new Mail();
        mail.setRecipient(newData.getEmail());
        mail.setSubject("歡迎加入夏虎茶");
        mailUtil.sendAttachmentsMail(newData,mail);
    }

}
