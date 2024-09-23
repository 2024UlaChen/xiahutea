package idv.tia201.g2.web.user.service;


import idv.tia201.g2.web.user.dto.TotalUserDTO;
import idv.tia201.g2.web.user.vo.Administrator;
import idv.tia201.g2.web.user.vo.TotalUsers;

public interface AdminService {
    public TotalUsers login(Administrator admin);
}
