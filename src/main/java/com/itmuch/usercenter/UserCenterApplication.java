package com.itmuch.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @MapperScan("com.itmuch") 扫描 mybatis 里面的包的接口
 * @EnableDiscoveryClient 通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能 , Edgware版本开始可以不使用该注释
 * @EnableBinding(Sink.class) 实现 Spring Cloud Stream 【消费】消息注解
 */
@MapperScan("com.itmuch")
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Sink.class)
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

    /**
     * RocketMQ 实现消息消费
     * · 01).pom.xml 中引入依赖： rocketmq-spring-boot-starter
     * · 02).Application启动类上写注解：暂时不需要
     * · 03).application.yml 中实现配置：rocketmq.name-server
     * · 方法实现类：AddBonusListener
     */

    /**
     * Spring Cloud Stream RocketMQ 实现消息消费
     * · 01).pom.xml 中引入依赖：spring-cloud-starter-stream-rocketmq
     * · 02).Application启动类上写注解：@EnableBinding(Sink.class)
     * · 03).application.yml 中实现配置：spring.cloud.stream.rocketmq.binder 和 spring.cloud.stream.bindings
     * · 04).编写测试实现代码： TestStreamConsumer#receive()
     *      @StreamListener(Sink.INPUT) : 通过注解 @StreamListener 实现多监听方法调度
     *
     *
     */

}
