package idv.tia201.g2.web.cart.service.impl;

import java.lang.reflect.Field;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import ecpay.payment.integration.ecpayOperator.EcpayFunction;
import idv.tia201.g2.web.cart.service.EcPayService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class EcPayServiceimpl implements EcPayService {

    @Override
    public String ecpayCheckout(Integer totalAmount) {
        //建立基本MerchanId hashket hashIV AllinOne 繼承AllinonBase
        AllInOne aio = new AllInOne();
        AioCheckOutALL obj = new AioCheckOutALL();
        //生成隨機訂單號
        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        // 生成當下時間，格式為 yyyy/MM/dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDate = sdf.format(new Date());

        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate(currentDate);
        obj.setTotalAmount(String.valueOf(totalAmount));
        obj.setTradeDesc("test Description");
        obj.setItemName("商品總額");
        obj.setNeedExtraPaidInfo("Y");
        //檢查碼
        String MerchanId = "3002599";
        String hashiv = "hT5OJckN45isQTTs";

        printObjectProperties(obj);
        obj.setReturnURL("http://localhost:8080/cms/ecPayReturn.html");
        obj.setOrderResultURL("http://localhost:8080/result"); // 前端跳轉 URL
//        String macvalue =EcpayFunction.genCheckMacValue(MerchanId,hashiv,obj);
//        System.out.println("MacValue:"+macvalue);
        // 產生支付表單
        String form = aio.aioCheckOut(obj, null);
        return form;
    }
    private void printObjectProperties(Object obj) {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 設置可訪問
            try {
                Object value = field.get(obj);
                System.out.println(field.getName() + " = " + value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
