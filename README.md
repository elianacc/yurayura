## 前言

此项目为个人兴趣编写，实现了spring cloud alibaba的基本搭建及使用，结合所学实现了一些系统的基本功能（系统数据字典，系统菜单设置，系统管理员角色分配，系统角色分配权限，系统权限管理，系统组织管理等）和两个增删改查+导入导出模块样例

**此项目对应B端Vue项目地址： https://github.com/elianacc/yurayura-business-vue/tree/yurayura-cloud-business-vue**

## 项目目录介绍

```apl
yura-cloud
├── yura-cloud-api ----- api调用模块
├── yura-cloud-auth ----- 认证授权模块
├── yura-cloud-codegenerator ----- 代码生成模块
├── yura-cloud-common ----- 通用模块
├───── yura-cloud-common-core ----- 核心模块
├───── yura-cloud-common-feign ----- feign模块
├───── yura-cloud-common-mail ----- mail模块
├───── yura-cloud-common-mqtt ----- mqtt模块
├───── yura-cloud-common-mybatisplus ----- mybatis-plus模块
├───── yura-cloud-common-nacos ----- nacos模块
├───── yura-cloud-common-redis ----- redis模块
├───── yura-cloud-common-satoken ----- sa-token模块
├───── yura-cloud-common-seata ----- seata模块
├───── yura-cloud-common-sentinel ----- sentinel模块
├───── yura-cloud-common-swagger ----- swagger模块
├── yura-cloud-gateway ----- 网关模块
└── yura-cloud-modules ----- 业务模块
```

## 项目技术构成

| 描述                | 框架                                | 版本                        |
| ------------------- | ----------------------------------- | --------------------------- |
| 基础                | Spring Boot                         | 2.3.12.RELEASE              |
| 微服务基础          | Spring Cloud + Spring Cloud Alibaba | Hoxton.SR12 + 2.2.6.RELEASE |
| 服务注册与配置中心  | Nacos                               | 2.0.3                       |
| 服务调用            | OpenFeign                           | 2.2.9.RELEASE               |
| 分布式事务          | Seata                               | 1.3.0                       |
| 服务网关            | Gateway                             | 2.2.9.RELEASE               |
| 数据库持久层（ORM） | MyBatis-Plus                        | 3.5.1                       |
| 自动生成代码工具    | MyBatis-Plus-Generator              | 3.5.1                       |
| 分页工具            | Mybatis-PageHelper                  | 5.2.0                       |
| 认证和授权          | Sa-Token + Jwt                      | 1.35.0.RC                   |
| 消息发布订阅        | MQTT                                | 5.3.8.RELEASE               |
| 分布式锁            | Lock4j                              | 2.2.2                       |
| 日志                | Logback                             | 1.2.3                       |
| 项目构建            | Maven                               | 3.6.3                       |
| 代码简化工具        | Lombok                              | 1.18.20                     |
| 数据库连接池        | HikariCP                            | 3.4.5                       |
| 数据库              | MySQL80                             | 8.0.28                      |
| NoSQL数据库         | Redis                               | 3.2.100                     |
| 在线接口文档生成    | Knife4j（swagger2增强）             | 3.0.3                       |
| 文档处理            | easypoi                             | 4.2.0                       |
| 工具类库            | Hutool                              | 5.8.9                       |

## 常用功能实现

-  全局捕获异常处理(包括系统异常500、自定义业务异常403、请求接口参数异常400、断言异常等)
-  AOP统一处理Web请求日志
-  redis锁注解处理接口流控
-  ~~分布式session共享~~   →  现转为无状态模式，使用 Jwt
-  外部静态资源访问处理
-  通用接口返回信息处理
-  发送Email工具
-  redis常用操作工具
-  跨域处理
-  系统数据字典
-  系统菜单设置
-  系统管理员组织下角色分配
-  系统组织角色分配权限
-  系统权限管理
-  系统组织管理
-  Excel按模板或按注解导出、Excel带校检的导入

## 项目业务模块Nacos统一配置

```yaml
# SpringBoot内嵌web容器配置
server:
  # SpringBoot内嵌tomcat配置
  tomcat:
    # uri编码
    uri-encoding: UTF-8
    # 连接建立超时时间（单位：ms）
    connection-timeout: 12000
    # 最大等待队列长度：每个请求使用一个线程，线程数超过最大链接数后请求会进入等待队列，直到有线程处理
    accept-count: 1000
    # 线程数设置
    threads:
      # 最小线程数：SpringBoot启动时初始化的线程数量
      min-spare: 100
      # 最大线程数：可以设为CPU线程数的200~250倍
      max: 500
    # 最大连接数：Tomcat同一时间能接受的最大线程数量，需要大等于 max-threads + accept-count
    max-connections: 1500
  # 编码配置（编码过滤器）
  servlet:
    encoding:
      charset: UTF-8
      force-request: true
      force-response: true
      enabled: true

spring:  
  # 数据源配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/yurayura_sys?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
    username: root
    password: 123456
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      # 此属性控制从池返回的连接的默认自动提交行为，默认值：true
      auto-commit: true
      # 空闲连接存活最大时间，默认600000（10分钟）
      idle-timeout: 60000
      # 测试连接的活动性的最长时间，默认值：5000
      validation-timeout: 3000
      # 最小空闲连接数量，默认是10
      minimum-idle: 10
      # 连接池最大连接数，默认是10
      maximum-pool-size: 20
      # 此属性控制池中连接的最长生命周期，值0表示无限生命周期，默认1800000即30分钟
      max-lifetime: 65000
      # 数据库连接超时时间，默认30秒，即30000
      connection-timeout: 30000
      connection-test-query: SELECT 1
      pool-name: ELiaNaCcHikariCP
  # thymeleaf配置
  thymeleaf:
    mode: HTML
    encoding: UTF-8
    cache: false
  # 发送邮箱配置
  mail:
    host: smtp.126.com
    port: 587
    username: （隐藏）@126.com
    password: （隐藏）
    default-encoding: UTF-8
    properties:
      mail:
        smtp:
          auth: true
          socketFactory:
            class: javax.net.ssl.SSLSocketFactory
          starttls:
            enable: true
  # 文件上传配置
  servlet:
    multipart:
      # 上传文件位置
      location: D://yurayura-upload-resource/upload
      # 单个文件的最大值
      max-file-size: 10MB
      # 总上传文件的最大值
      max-request-size: 10MB
  # Redis配置
  redis:
    host: 127.0.0.1
    port: 6379
    password: 123456
    database: 11
    # 连接超时时间
    timeout: 10s
    lettuce:
      pool:
        # 连接池最大连接数
        max-active: 200
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: -1ms
        # 连接池中的最大空闲连接
        max-idle: 10
        # 连接池中的最小空闲连接
        min-idle: 0

# openfeign设置
feign:
  client:
    config:
      default:
        # 指的是建立连接后从服务器读取到可用资源所用的时间
        connect-timeout: 5000
        # 指的是建立连接所用的时间，适用于网络状况正常的情况下,两端连接所用的时间
        read-timeout: 12000

# seata配置
seata:
  enabled: true
  application-id: ${spring.application.name}
  tx-service-group: my_test_tx_group
  enable-auto-data-source-proxy: true
  registry:
    type: nacos
    nacos:
      application: seata-server
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      cluster: yurayura-cloud
      username: nacos
      password: nacos
  config:
    type: nacos
    nacos:
      server-addr: localhost:8888
      namespace: ad0670cf-4455-42e9-a7ef-90937cd83f0b
      group: SEATA_GROUP
      username: nacos
      password: nacos

logging:
  # 日志级别设置
  level:
    # 打印sql
    pers.elianacc.yurayura.dao: debug
    # feign日志以什么级别监控哪个接口
    pers.elianacc.yurayura.feign.**: debug
    io:
      seata: info

# mybatis plus配置
mybatis-plus:
  mapper-locations: classpath:pers/elianacc/yurayura/dao/**/*.xml

# lock4j配置
lock4j:
  acquire-timeout: 3000
  expire: 5000
  primary-executor: com.baomidou.lock.executor.RedisTemplateLockExecutor
  lock-key-prefix: lock4j

# knife4j配置
knife4j:
  # 开启增强配置
  enable: true
  # 开启Swagger的Basic认证功能,默认是false
  basic:
    enable: true
    # Basic认证用户名
    username: admin
    # Basic认证密码
    password: admin123

# SpringBoot指标监控配置
management:
  endpoints:
    # 是否开启所有监控端点
    enabled-by-default: false
    # 以web方式暴露所有监控端点
    web:
      exposure:
        include: '*'
  endpoint:
    # 健康状况端点
    health:
      enabled: true
      show-details: always
    # SpringBean端点
    beans:
      enabled: true
    # 环境端点
    env:
      enabled: true
    # 应用信息端点
    info:
      enabled: true
    # 日志信息端点
    loggers:
      enabled: true
    # 指标信息端点
    metrics:
      enabled: true

# sa-token配置
sa-token:
  # jwt秘钥
  jwt-secret-key: qazxcvbnmwertyuiopsdfghjkl
  # token 名称（同时也是 cookie 名称）
  token-name: satoken
  # token 有效期（单位：秒） 默认30天，-1 代表永久有效
  timeout: 2592000
  # token 最低活跃频率（单位：秒），如果 token 超过此时间没有访问系统就会被冻结，默认-1 代表不限制，永不冻结
  active-timeout: -1
  # 是否允许同一账号同端多地同时登录 （为 true 时允许一起登录, 为 false 时新登录挤掉旧登录）
  is-concurrent: true
  # 在多人登录同一账号时，是否共用一个 token （为 true 时所有登录共用一个 token, 为 false 时每次登录新建一个 token）
  is-share: false
  # token 风格（默认可取值：uuid、simple-uuid、random-32、random-64、random-128、tik）
  token-style: uuid
  # 是否输出操作日志
  is-log: true

# 自定义配置
yurayura:
  # swagger是否启用
  swagger:
    enable: true
  # 接收邮箱
  receive-email: （隐藏）@126.com
  # mqtt配置
  mqtt:
    hostUrl: tcp://127.0.0.1:1883
    username: yura
    password: 123456
    clientId: mqtt-yura-cloud-sys
    cleanSession: true
    reconnect: true
    connectTimeout: 100
    keepAlive: 100
    qos: 0
    subTopic: yura-cloud-sys/#

```

## 项目网关模块Nacos配置

```yaml
spring:
  cloud:
    gateway:
      discovery:
        locator:
          lowerCaseServiceId: true
          enabled: true  
      # 网关路由设置
      routes:
        - id: yura-cloud-sys
          uri: lb://yura-cloud-sys
          predicates:
            - Path=/api/sys/**
        - id: yura-cloud-comic
          uri: lb://yura-cloud-comic
          predicates:
            - Path=/api/comic/**
        - id: yura-cloud-user
          uri: lb://yura-cloud-user
          predicates:
            - Path=/api/user/**

# SpringBoot指标监控配置
management:
  endpoints:
    # 是否开启所有监控端点
    enabled-by-default: false
    # 以web方式暴露所有监控端点
    web:
      exposure:
        include: '*'
  endpoint:
    # 健康状况端点
    health:
      enabled: true
      show-details: always
    # SpringBean端点
    beans:
      enabled: true
    # 环境端点
    env:
      enabled: true
    # 应用信息端点
    info:
      enabled: true
    # 日志信息端点
    loggers:
      enabled: true
    # 指标信息端点
    metrics:
      enabled: true

# 自定义配置
yurayura:
  upload-file:
    # 上传文件对外暴露的访问路径(虚拟路径)
    virtual-path: /upload
    # 上传文件真实路径
    real-path: D://yurayura-upload-resource/upload
```

## 开发工具

-  开发环境： JDK 1.8
-  编码： IntelliJ IDEA（idea）
-  数据库可视化： Navicat Premium
-  Redis可视化： RedisDesktopManager
-  mqtt服务端： EMQX
-  maven私服工具： Nexus
-  密钥生成工具： KeyTool
-  页面访问及调试： Google Chrome
-  markdown： Typora
-  截图： Snipaste































