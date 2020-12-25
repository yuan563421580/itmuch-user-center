package com.itmuch.usercenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;


/**
 * 使用 Stream 接收消息
 * application.yml 中配置 topic 和 group
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class NewAddBonusListener{

    private final UserService userService;

    @StreamListener(MyMqSink.MY_MQ_INPUT)
    public void receive(UserAddBonusMsgDTO message) {
        log.info("通过stream收到了消息：messageBody = {}", JSON.toJSONString(message));

        // 当收到消息的时候，执行的业务

        // 封装之前的方法，实现本地事务
        userService.addBonus(message);

    }

}
