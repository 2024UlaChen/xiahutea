package idv.tia201.g2.web.user.service;


import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.Administrator;

public interface AdminService {
    TotalUserDTO login(Administrator admin);
}
