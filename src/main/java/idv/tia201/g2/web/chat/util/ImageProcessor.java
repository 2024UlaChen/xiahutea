package idv.tia201.g2.web.chat.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageProcessor {
    public static String encodeImage(byte[] imageData) throws IOException {
        // 根據圖片的字節數據獲取 BufferedImage
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bais);

        // 根據圖片格式生成 MIME 類型
        String format = ImageIO.getImageReadersByFormatName("jpeg").hasNext() ? "jpeg" : "png";
        String mimeType = "image/" + format;

        // 將字節數據編碼為 Base64
        String base64Image = Base64.getEncoder().encodeToString(imageData);

        // 根據 MIME 類型加上正確的前綴
        String base64ImageWithPrefix = "data:" + mimeType + ";base64," + base64Image;

        return base64ImageWithPrefix;
    }
}
