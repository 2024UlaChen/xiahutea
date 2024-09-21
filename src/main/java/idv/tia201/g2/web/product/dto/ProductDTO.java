package idv.tia201.g2.web.product.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder

public class ProductDTO {

        private String productName;
        private Integer productPrice;
        private boolean productStatus;
        private Integer productStoreId;
        private Integer productCategoryId;
        private MultipartFile productPictureFile;
        private boolean normalIce;
        private boolean lessIce;
        private boolean iceFree;
        private boolean lightIce;
        private boolean roomTemperature;
        private boolean hot;
        private boolean fullSugar;
        private boolean lessSugar;
        private boolean halfSugar;
        private boolean quarterSugar;
        private boolean noSugar;
        private boolean pearl;
        private boolean pudding;
        private boolean coconutJelly;
        private boolean taro;
        private boolean herbalJelly;
        private String size;

        // Getters and Setters
    }


