package com.itmuch.usercenter;

import com.itmuch.usercenter.rocketmq.MyMqSink;
import com.itmuch.usercenter.rocketmq.MySink;
import com.itmuch.usercenter.rocketmq.MyTagsSink;
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
@EnableBinding({Sink.class, MySink.class, MyMqSink.class, MyTagsSink.class})
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

    /**
     * 微服务的用户认证与授权
     *
     * 有状态 vs 无状态
     *  · 有状态：优点：服务器端控制力强。
     *          缺点：存在中心店；迁移麻烦；服务器端存储，加大了服务器的压力。
     *  · 无状态：优点：去中心化；无存储，简单；任意扩容、缩容。
     *          缺点：服务端控制力相对弱。
     *
     *
     * JWT是什么：JWT全称Json web token, 是一个开放标准（RFC 7519）,用来在各方之间安全地传输信息。
     *          JWT可被验证和信任，因为它是数字签名的。
     * JWT组成：（组成 : 作用 : 内容示例）
     *      Header(头) : 记录令牌类型、签名的算法等 : {"alg":"HS256","type":"JWT"}
     *      Payload(有效载荷) : 携带一些用户信息 : {"userId":"1","username":"damu"}
     *      Signature(签名) : 防止token被篡改、确保安全性 : 计算出来的签名，一个字符串
     *
     * JWT公式:
     *  · Token = Base64(Header).Base64(Payload).Base64(Signature)
     *      事例：aaaa.bbbbb.ccccc
     *  · Signature = Header指定的签名算法(Base64(Header).Base64(Payload),秘钥)
     *      事例：HS256(aaaa.bbbbb,秘钥)
     *
     * JWT操作工具类：
     *  ~ 手记：https://www.imooc.com/article/290892
     * 用户中心引入JWT实现逻辑
     *  · 01).pom.xml 中引入依赖：jjwt-api 、 jjwt-impl 、 jjwt-jackson
     *  · 02).创建工具包：JwtOperator
     *  · 03).application.yml中配置jwt：jwt.secret:秘钥 和 jwt.expire-time-in-second:有效期
     * 内容中心按照上述整合jwt , 注意秘钥[jwt.secret:秘钥]需要保持一致
     *
     * AOP实现登录检查
     *  · 01).引入依赖：spring-boot-starter-aop
     *  · 02).创建注解：CheckLogin
     *  · 03).创建切面：CheckLoginAspect
     * 统一管理异常：
     *  · 01).GlobalExceptionErrorHandler
     */

    /**
     * 业界流行的调用链监控工具
     *  · Spring Cloud Sleuth + Zipkin
     *  · Skywalking 、Pinpoint
     *
     * ---
     *
     * Sleuth
     *  · 什么是 Sleuth ：Sleuth 是一个 Spring Cloud 的分布式跟踪解决方案。
     *  · Sleuth 术语 ：
     *      ~ Span(跨度): Sleuth的基本工作单元，它用一个64位的id唯一标识。
     *                  除id外，Span还包含其他数据，例如描述、时间戳事件、键值对的注解（标签）、spanId、span父ID等。
     *      ~ trace(跟踪): 一组span组成的树状结构称为 trace。
     *      ~ Annotation(标注):
     *          - CS(Client Sent客户端发送)：客户端发起一个请求，该annotation描述了span的开始。
     *          - SR(Server Received服务器端接收)：服务器端获得请求并准备处理它。
     *          - SS(Server Sent服务器端发送)：该annotation表明完成请求处理（当响应发回客户端时）。
     *          - CR(Client Received客户端接收)：span结束的标识。客户端成功接收到服务器端的响应。
     *
     * 整合Sleuth
     *  · 01).引入依赖：spring-cloud-starter-sleuth
     *        不需要注解和配置。
     *  · 02).启动进行测试：观察控制台日志：[user-center,,,]
     *
     * ---
     *
     * Zipkin
     *  · Zipkin是什么：Zipkin是Twitter开源的分布式跟踪系统，主要用来收集系统的时序数据，从而追踪系统的调用问题。
     *          通俗的讲Zipkin是前面分析的数据库和界面。
     *  · 搭建 Zipkin Server
     *      ~ 手记：https://www.imooc.com/article/291572
     *      ~ 访问地址：http://39.102.66.189:9411/
     * 整合Sleuth
     *  · 01).引入依赖：spring-cloud-starter-zipkin
     *      注意：当整合zipkin后就不需要sleuth，原因是zipkin依赖包含sleuth
     *  · 02).application.yml中配置：spring.zipkin.base-url
     *                             spring.sleuth.sampler.probability
     *
     * 解决Spring Cloud Alibaba/Spring Cloud整合Zipkin之后的报错问题（本工程版本没有此问题）
     *  错误信息：ERROR [user-center,,,] 48628 --- [.naming.updater] com.alibaba.nacos.client.naming          :
     *              [NA] failed to update serviceName: DEFAULT_GROUP@@localhost
     *  ~ 手记：https://www.imooc.com/article/291578
     *  · 原因：Spring Cloud把 http://localhost:9411/ 当作了服务发现组件里面的服务名称；
     *          于是，Nacos Client尝试从Nacos Server寻找一个名为 localhost:9411 的服务…这个服务根本不存在啊，
     *          于是就疯狂报异常（因为Nacos Client本地定时任务，刷新本地服务发现缓存）
     *  · 解决方案：方案1.让Spring Cloud 正确识别 http://localhost:9411/ ，当成一个URL，而不要当做服务名。
     *            方案2.把Zipkin Server注册到Nacos
     *  · 谈谈Zipkin Server注册到服务发现组件：Zipkin Server 官方并不支持注册到服务发现组件！！！！
     *              在 https://github.com/openzipkin/zipkin/issues/2540 里面。
     *  · 配置实现：spring.zipkin.discovery-client-enabled: false
     *
     */


}
