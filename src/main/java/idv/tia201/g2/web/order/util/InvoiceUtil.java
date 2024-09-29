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
public class InvoiceUtil {

    //    AES 加密的強度設定方式: 128 bit
    //    CipherMode: CBC
    //    PaddingMode: PKCS7

    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding"; // 等效於 PKCS7Padding
    private static final String HashKey = "ejCk326UnaZWKisg";
    private static final String HashIV = "q9jcZX8Ib9LM8wYk";

    // AES加密方法
    public static String encryptAES(String jsonData) {
        // JSON字串 > URL編碼大寫
        String urlData = URLEncoder.encode(jsonData, StandardCharsets.UTF_8);

        // URL編碼大寫 > AES加密。
        try {
            byte[] key = HashKey.getBytes(StandardCharsets.UTF_8);
            byte[] iv = HashIV.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);  // SecretKeySpec 類表示密鑰使用AES規格。
            IvParameterSpec ivSpec = new IvParameterSpec(iv);  // IvParameterSpec 類表示初始向量 (IV)，用於 CBC 模式。

            // 1. Cipher類提供加密和解密功能 ||  2. AES_CBC_PKCS7_PADDING 表示加密算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // 1. 加密模式 (Cipher.ENCRYPT_MODE) || 2. 並傳入密鑰規格 (keySpec) 和初始向量 (ivSpec)。
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // cipher.doFinal(...): 執行加密操作返回加密後的字串陣列
            byte[] encryptedBytes = cipher.doFinal(urlData.getBytes(StandardCharsets.UTF_8));
            // 使用 Base64 編碼將 加密後的字串陣列轉換為字串。
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
            byte[] key = HashKey.getBytes(StandardCharsets.UTF_8);
            byte[] iv = HashIV.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES);  // SecretKeySpec 類表示密鑰使用AES規格。
            IvParameterSpec ivSpec = new IvParameterSpec(iv);  // IvParameterSpec 類表示初始向量 (IV)，用於 CBC 模式。

            // 1. Cipher類提供加密和解密功能 ||  2. AES_CBC_PKCS7_PADDING 表示加密算法、模式和填充方式
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
            // 1. 解密模式 (Cipher.DECRYPT_MODE) || 2. 傳入密鑰規格 (keySpec) 和初始向量 (ivSpec)。
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 使用 Base64 將加密字串 解碼為字串陣列
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            // 執行解密操作 返回解密後的字串陣列
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            // 將解密後的字串陣列轉 為URL編碼字串
            String urlData = new String(decryptedBytes, StandardCharsets.UTF_8);

            //  URL編碼大寫 > JSON字串
            String jsonData = URLDecoder.decode(urlData, StandardCharsets.UTF_8);

            return jsonData;

        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
