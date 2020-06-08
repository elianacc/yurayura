### YuraYura项目简介及技术构成（2020.04.08）



##### 简介：

YuraYura是一个兴趣使然的项目，主要的功能是做番剧的导视，数据的应用

![Snipaste_2019-12-25_10-22-31.png](https://i.loli.net/2020/04/27/TDYwVsvpH2oBRu8.png) 



##### 前端：

- ​    Bootstrap 4
- ​    Vue.js（多页应用）
- ​    jQuery
- ​    thymeleaf（主要用于组合html碎片，数据绑定使用vue）

##### 后端：

- ​    总体： SpringBoot2.x
- ​    数据库持久层： mybatis + mybatis plus
- ​    自动生成代码工具： mybatis plus generator（mpg）
- ​    分页工具： mybatis pagehelper
- ​    日志： logback
- ​    项目构建： Maven
- ​    代码简化工具： lombok
- ​    连接池： HikariCP
- ​    数据库： MySQL80
- ​    NoSQL数据库： Redis
- ​    在线接口文档生成： Swagger2 + apiggs
- ​    JSON处理工具： fastjson

##### 其他：

- ​    全局捕获异常处理
- ​    AOP统一处理Web请求日志
- ​    防止重复提交操作
- ​    通用返回信息处理 vo
- ​    发送Email服务
- ​    系统数据字典

##### 开发工具：

- ​    开发环境： JDK 1.8
- ​    编码： IntelliJ IDEA（idea）
- ​    数据库： Navicat Premium
- ​    Redis可视化： RedisDesktopManager
- ​    调试： Google Chrome
- ​    markdown： Typora
- ​    截图： Snipaste

