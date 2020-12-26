package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * Spring Cloud Stream 【消费】消息测试类
 *
 * @StreamListener(Sink.INPUT)
 *      为初始正常测试消息消费。
 *
 * @StreamListener(value = Sink.INPUT, condition = "headers['my-header']=='my-condition-header'")
 *      实现消息过滤：condition 方式。
 */
@Service
@Slf4j
public class TestStreamConsumer {

    //@StreamListener(Sink.INPUT)
    @StreamListener(value = Sink.INPUT, condition = "headers['my-header']=='my-condition-header'")
    public void receive(String messageBody) {
        log.info("通过stream收到了消息：messageBody = {}", messageBody);
        // throw new IllegalArgumentException("抛异常");
    }


    // 测试全局异常时候使用
    @StreamListener("errorChannel")
    public void error(Message<?> message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        log.warn("发生异常，errorMessage = {}", errorMessage);
    }


}
