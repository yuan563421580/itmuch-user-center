package com.itmuch.usercenter.controller.user;

import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.domain.dto.user.UserAddBonseDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import com.itmuch.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BonusController {

    private final UserService userService;

    @PutMapping("/add-bonus")
    public User addBonus(@RequestBody UserAddBonseDTO userAddBonseDTO) {
        Integer userId = userAddBonseDTO.getUserId();
        userService.addBonus(
                UserAddBonusMsgDTO.builder()
                        .userId(userId)
                        .bonus(userAddBonseDTO.getBonus())
                        .build()
        );
        return this.userService.findById(userId);
    }

}
