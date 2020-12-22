package com.itmuch.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @MapperScan("com.itmuch") 扫描 mybatis 里面的包的接口
 * @EnableDiscoveryClient 通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能 , Edgware版本开始可以不使用该注释
 */
@MapperScan("com.itmuch")
@SpringBootApplication
@EnableDiscoveryClient
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

    /**
     * RocketMQ 实现消息消费
     * · pom.xml 中引入依赖： rocketmq-spring-boot-starter
     * · application.yml 中实现配置：rocketmq.name-server
     * · 方法实现类：AddBonusListener
     */

}
