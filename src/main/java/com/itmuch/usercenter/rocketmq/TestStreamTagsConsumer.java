package com.itmuch.usercenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestStreamTagsConsumer {

    /**
     * 我消费带有 tag1 的消息
     *
     * @param messageBody 消息体
     */
    @StreamListener(MyTagsSink.MY_TAGS_INPUT1)
    public void receive1(String messageBody) {
        log.info("带有tag1的消息被消费了：messageBody = {}", messageBody);
    }

    /**
     * 我消费带有 tag1 或 tag2 的消息
     *
     * @param messageBody 消息体
     */
    @StreamListener(MyTagsSink.MY_TAGS_INPUT2)
    public void receive2(String messageBody) {
        log.info("带有tag2/tag3的消息被消费了：messageBody ={}", messageBody);
    }

}
