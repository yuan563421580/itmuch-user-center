package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * Spring Cloud Stream 接口自定义【消费】消息测试类
 */
@Service
@Slf4j
public class TestStreamMySourceConsumer {

    @StreamListener(MySink.MY_INPUT)
    public void receive(String messageBody) {
        log.info("自定义接口消费：通过stream收到了消息：messageBody = {}", messageBody);
    }

}
