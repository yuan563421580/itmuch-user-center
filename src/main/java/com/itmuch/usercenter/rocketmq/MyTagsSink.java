package com.itmuch.usercenter.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 测试 Tags 方式消息过滤新创建自定义消息消费接口
 */
public interface MyTagsSink {

    String MY_TAGS_INPUT1 = "my_tags_input1";
    String MY_TAGS_INPUT2 = "my_tags_input2";

    @Input(MY_TAGS_INPUT1)
    SubscribableChannel input();

    @Input(MY_TAGS_INPUT2)
    SubscribableChannel input2();

}
