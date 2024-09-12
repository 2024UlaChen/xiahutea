package idv.tia201.g2.core.vo;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressVo {
    private List<AddressDetailVo> data;

}
