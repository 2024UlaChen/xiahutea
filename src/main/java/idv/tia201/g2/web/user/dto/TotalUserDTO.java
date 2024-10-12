package idv.tia201.g2.web.user.dto;

import idv.tia201.g2.core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalUserDTO extends Core {
    private Long totalUserId;       // 0:消費者 、 1:店家 、 3:管理員
    private Integer userTypeId;
    private Integer userId;
    private byte[] logo;

    @Override
    public String toString() {
        return "TotalUserDTO{" +
                "totalUserId=" + totalUserId +
                ", userTypeId=" + userTypeId +
                ", userId=" + userId +
                ", logo=" + Arrays.toString(logo) +
                '}';
    }
}
