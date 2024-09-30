package idv.tia201.g2.web.order.util;

import org.springframework.stereotype.Service;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class InvoiceAesUtil {
    //    AES 加密的強度設定方式: 128 bit
    //    CipherMode: CBC
    //    PaddingMode: PKCS7

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding"; // 等於 PKCS7 Padding
    private static final String HashKey = "ejCk326UnaZWKisg";
    private static final String HashIV = "q9jcZX8Ib9LM8wYk";

    // AES加密方法
    public static String encryptAES(String jsonData) {
        try {
            // 1. JSON字串 > URL編碼大寫
            String urlData = URLEncoder.encode(jsonData, StandardCharsets.UTF_8);

            // 2. URL編碼大寫 > AES加密
            byte[] key = HashKey.getBytes(StandardCharsets.UTF_8);
            byte[] iv = HashIV.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);  // 密鑰規格： SecretKeySpec 表示密鑰使用AES規格
            IvParameterSpec ivSpec = new IvParameterSpec(iv);     // 初始向量： IvParameterSpec 表示初始向量(IV)，用於CBC模式

            // (1) Cipher提供加密和解密功能  || (2) AES_CBC_PKCS5_PADDING 表示加密算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // (1) 初始化加密模式Cipher.ENCRYPT_MODE || (2) 傳入密鑰規格(keySpec) 和初始向量(ivSpec)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // URL編碼字串 > 字串陣列
            byte[] decryptedBytes = urlData.getBytes(StandardCharsets.UTF_8);
            // cipher.doFinal(...): 進行加密操作
            byte[] encryptedBytes = cipher.doFinal(decryptedBytes);
            // 使用Base64編碼： 加密字串陣列 > 加密字串
            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);
            return encryptedData;

        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // AES解密方式
    public static String decryptAES(String encryptedData) {
        try {
            // 1. URL編碼大寫 > AES加密
            byte[] key = HashKey.getBytes(StandardCharsets.UTF_8);
            byte[] iv = HashIV.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);  // 密鑰規格： SecretKeySpec 表示密鑰使用AES規格
            IvParameterSpec ivSpec = new IvParameterSpec(iv);     // 初始向量： IvParameterSpec 表示初始向量(IV)，用於CBC模式

            // (1) Cipher提供加密和解密功能  || (2) AES_CBC_PKCS5_PADDING 表示加密算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // (1) 初始化解密模式 Cipher.DECRYPT_MODE || (2) 傳入密鑰規格(keySpec) 和初始向量(ivSpec)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 使用Base64編碼： 加密字串 > 加密字串陣列
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            // cipher.doFinal(...): 進行解密操作
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            // 字串陣列 >  URL編碼字串
            String urlData = new String(decryptedBytes, StandardCharsets.UTF_8);

            // 2. URL編碼大寫 > JSON字串
            String jsonData = URLDecoder.decode(urlData, StandardCharsets.UTF_8);
            return jsonData;

        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
