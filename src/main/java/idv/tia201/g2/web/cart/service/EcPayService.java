package idv.tia201.g2.web.cart.service;

import jakarta.servlet.http.HttpSession;

public interface EcPayService {
    public String ecpayCheckout(Integer totalAmount, HttpSession session);
}
