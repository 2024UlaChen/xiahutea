package idv.tia201.g2.web.order.dto;

import lombok.*;

@Data
public class OMemberDto {

    private Integer customerId;
    private String customerAccount;
    private String nickname;
    private String customerEmail;
    private String customerPhone;
    private Integer customerMoney;

}
