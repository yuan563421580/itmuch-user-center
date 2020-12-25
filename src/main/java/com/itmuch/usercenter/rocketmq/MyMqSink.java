package com.itmuch.usercenter.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 可以不用新建 但是为了保留之前的代码 新建一个
 */
public interface MyMqSink {

    String MY_MQ_INPUT = "my-mq-input";

    @Input(MY_MQ_INPUT)
    SubscribableChannel input();

}
