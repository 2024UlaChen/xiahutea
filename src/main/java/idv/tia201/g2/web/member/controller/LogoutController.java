package idv.tia201.g2.web.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("member/logout")
public class LogoutController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutController.class);

    @GetMapping
    public void logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
    }

}
