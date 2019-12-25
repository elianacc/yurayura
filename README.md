### YuraYura项目简介及技术构成（2019.11.09）



##### 简介：

YuraYura是一个兴趣使然的项目，主要的功能是做番剧的导视，数据的应用

![Snipaste_2019-12-25_10-22-31.png](https://i.loli.net/2019/12/25/ZdQiAefXL5SmHcb.png) 



##### 前端：

- ​    thymeleaf
- ​    VUE
- ​    jQuery
- ​    Bootstrap 4

##### 后端：

- ​    总体：SpringBoot2.x
- ​    数据库持久层：mybatis + mybatis plus
- ​    自动生成代码工具：mybatis plus generator
- ​    分页工具：mybatis pagehelper
- ​    日志：logback
- ​    jar包整合：maven
- ​    代码简化工具：lombok
- ​    连接池：HikariCP
- ​    数据库：MySQL80
- ​    在线接口文档生成： Swagger2
- ​    JSON处理工具：fastjson

##### 其他：

- ​    全局捕获异常处理
- ​    AOP统一处理Web请求日志
- ​    防止重复提交数据
- ​    通用返回信息处理 vo
- ​    发送Email服务
