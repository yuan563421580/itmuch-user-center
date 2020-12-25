package com.itmuch.usercenter.service.user;

import com.alibaba.fastjson.JSON;
import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserMapper userMapper;

    private final BonusEventLogMapper bonusEventLogMapper;

    public User findById(Integer id) {
        log.info("我被请求了...");
        return userMapper.selectByPrimaryKey(id);
    }

    // 加加分操作 实现本地事务
    @Transactional(rollbackFor = Exception.class)
    public void addBonus(UserAddBonusMsgDTO msgDTO) {

        // 1.为用户加积分
        Integer userId = msgDTO.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        Integer bonus = msgDTO.getBonus();
        user.setBonus(user.getBonus() + bonus);
        userMapper.updateByPrimaryKeySelective(user);

        // 2.记录日志到bonus_event_log表里面
        bonusEventLogMapper.insert(
                BonusEventLog.builder()
                        .userId(userId)
                        .value(bonus)
                        .event("CONTRIBUTE")
                        .createTime(new Date())
                        .description("投稿加积分 NEW ...")
                        .build()
        );

        log.info("积分添加完毕 NEW...");

    }

}
