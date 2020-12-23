package com.itmuch.usercenter;

import com.itmuch.usercenter.rocketmq.MySink;
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
@MapperScan("com.itmuch.usercenter.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({Sink.class, MySink.class})
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
     *      特别注意：消费者的topic设定destination的值一定要与生产的topic值一致。否则无法消费。
     *      说明：group 设置声明：如果用的是RocketMQ，一定要设置 ； 如果是其他的MQ，可以为空。
     * · 04).编写测试实现代码： TestStreamConsumer#receive()
     *      @StreamListener(Sink.INPUT) : 通过注解 @StreamListener 实现多监听方法调度
     *
     * ---
     *
     * Spring Cloud Stream RocketMQ 接口自定义 实现消息消费
     *  在 user-center 模块实现 ;
     *  · 01).在上面 Spring Cloud Stream 编写【消费者】 基础上继续进行实现
     *  · 02).定义接口 MySink ：
     *  · 03).Application启动类上写注解(增加一个MySink)：@EnableBinding({Sink.class, MySink.class})
     *  · 04).application.yml 中增加配置：spring.cloud.stream.bindings.my-input
     *      ~ 特别注意：消费者的topic设定destination的值一定要与生产的topic值一致。否则无法消费。
     *      ~ 注意：配置文件中的 my-input 一定要与 接口 MySource 中注解 @Input(MY_INPUT) 中引用的值一致。
     *      ~ 说明：group 设置声明：如果用的是RocketMQ，一定要设置 ； 如果是其他的MQ，可以为空。
     *  · 05).编写测试实现代码：TestStreamMySourceConsumer#receive()
     *      @StreamListener(MySink.MY_INPUT) : 通过注解 @StreamListener 实现多监听方法调度。
     *
     * ---
     *
     * Spring Cloud Stream 本质：
     *  · Source 接口 ：是 Spring Cloud Stream 默认提供的一个消息发送的接口。 自定义的 MySource 接口没什么区别。
     *  · Sink 接口 ：是 Spring Cloud Stream 默认提供的一个消息接收的接口。 自定义的 MySink 接口没什么区别。
     *  · Processor 接口 ：继承了 Source 、Sink 接口。可以使用消息发送 、接收。
     *  · 本质：当我们定义好 Source/Sink 接口后，在启动类使用 EnableBinding 指定了接口后，
     *          就会使用 IOC 创建对应名字的代理类，所以配置文件中也必须同名。
     *
     */

}
