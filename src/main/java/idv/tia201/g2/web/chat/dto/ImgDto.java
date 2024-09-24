package idv.tia201.g2.web.chat.dto;

import lombok.Data;

@Data
public class ImgDto {
    private byte[] src;
    private String alt = "alt";
}
