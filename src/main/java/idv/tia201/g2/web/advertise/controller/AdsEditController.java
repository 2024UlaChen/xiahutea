package idv.tia201.g2.web.advertise.controller;


import idv.tia201.g2.web.advertise.dto.AdvertiseDto;
import idv.tia201.g2.web.advertise.service.AdsService;
import idv.tia201.g2.web.advertise.vo.Advertise;
import idv.tia201.g2.web.coupon.vo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

@RestController
@RequestMapping("/advertise")
public class AdsEditController {
    @Autowired
    private AdsService adsService;

    @PostMapping("/save")
    public Advertise save(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam(value = "imageUpload", required = false) MultipartFile file,
                          @RequestParam(value = "base64Image", required = false) String base64Image,
                          @RequestParam("homeDisplay") boolean homeDisplay,
                          @RequestParam("isActive") boolean isActive,
                          @RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime,
                          @RequestParam("adsId") Integer adsId,
                          @RequestParam("adsTotalUserid") long adsTotalUserId,
                          @RequestParam("roleTypeId") Integer roleTypeId) {
        Advertise ad = new Advertise();
        ad.setTitle(title);
        ad.setDescription(description);
        ad.setHomeDisplay(homeDisplay);
        ad.setIsactive(isActive);
        ad.setStartTime(parseDate(startTime));
        ad.setEndTime(parseDate(endTime));
        ad.setAdsId(adsId);
        ad.setAdsTotalUserid(adsTotalUserId);
        ad.setRoleTypeId(roleTypeId);
//        if (ad.getTitle()a == null) {
//            ad = new Advertise();
//            ad.setMessage("無廣告資訊");
//            ad.setSuccessful(false);
//            return ad;
//        }

        // 處理文件上傳
        if (file != null && !file.isEmpty()) {
            try {
                ad.setImgUrl(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                ad.setMessage("圖片處理失敗");
                ad.setSuccessful(false);
                return ad;
            }
        } else if (base64Image != null && !base64Image.isEmpty()) {
            try {
                // 解碼 Base64 圖片並將其存入 Advertise 物件
                byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
                ad.setImgUrl(decodedBytes);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                ad.setMessage("Base64 圖片處理失敗");
                ad.setSuccessful(false);
                return ad;
            }
        } else {
            ad.setMessage("必須提供圖片");
            ad.setSuccessful(false);
            return ad;
        }
//        System.out.println("Advertise Details:");
//        System.out.println("Title: " + ad.getTitle());
//        System.out.println("Description: " + ad.getDescription());
//        System.out.println("Image URL (byte array length): " + (ad.getImgUrl() != null ? ad.getImgUrl().length : 0));
//        System.out.println(ad.getStartTime());
//        System.out.println("homedisplay:"+ad.getHomeDisplay());
//        System.out.println("roletype:"+ad.getRoleTypeId());

        // 儲存廣告資訊到資料庫
        return adsService.saveAdvertise(ad);
    }
    @GetMapping("/edit/{adId}")
    public AdvertiseDto edit(@PathVariable Integer adId){

        Advertise ad = adsService.findAdvertiseById(adId);
        AdvertiseDto adDto = new AdvertiseDto();

        adDto.setAdsId(ad.getAdsId());
        adDto.setTitle(ad.getTitle());
        adDto.setDescription(ad.getDescription());
        adDto.setHomeDisplay(ad.getHomeDisplay());
        adDto.setIsactive(ad.getIsactive());
        adDto.setStartTime(ad.getStartTime());
        adDto.setEndTime(ad.getEndTime());
//        System.out.println("Timestamp: " + adDto.getStartTime());
//        System.out.println("Timestamp: " + adDto.getEndTime());
        // 將圖片的 byte[] 轉換為 Base64 字串
        if (ad.getImgUrl() != null) {
            String base64Image = Base64.getEncoder().encodeToString(ad.getImgUrl());
            adDto.setImgUrl(base64Image); // 將 Base64 圖片放入 DTO
        }
        return adDto;
    }

    //轉換日期為資料庫TimeStamp格式
    private Timestamp parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");  // 支援日期和時間格式
        try {
            return new Timestamp(sdf.parse(dateStr).getTime());  // 轉換為 Timestamp
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // 若解析失敗，返回 null 或處理錯誤
        }
    }

}
