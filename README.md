### YuraYura项目简介及技术构成



##### 简介：

> YuraYura是一个兴趣使然的项目，主要的功能是做番剧的导视，数据的应用
>
> PS: 本项目为yurayura项目的后端部分，前后端未分离，后会用Vue-cli重构
>

##### 前端：

- ​    Bootstrap 4
- ​    Vue.js（多页应用，后将采用vue-cli）
- ​    jQuery
- ​    thymeleaf（主要用于组合html碎片，数据绑定使用vue）

##### 后端：

- ​    总体： SpringBoot2.x
- ​    数据库持久层： mybatis + mybatis plus
- ​    自动生成代码工具： mybatis plus generator（mpg）
- ​    分页工具： mybatis pagehelper
- ​    安全框架： shiro
- ​    日志： logback
- ​    项目构建： Maven
- ​    代码简化工具： lombok
- ​    数据库连接池： HikariCP
- ​    数据库： MySQL80
- ​    NoSQL数据库： Redis
- ​    在线接口文档生成： Swagger2 + apiggs
- ​    JSON处理工具： fastjson
- ​    测试工具： junit4

##### 其他：

- ​    全局捕获异常处理
- ​    AOP统一处理Web请求日志
- ​    防止重复提交操作
- ​    通用返回信息处理 vo
- ​    发送Email服务
- ​    系统数据字典
- ​    外部静态资源访问处理
- ​    视图控制器
- ​    跨域处理

##### 开发工具：

- ​    开发环境： JDK 1.8
- ​    编码： IntelliJ IDEA（idea）
- ​    数据库可视化： Navicat Premium
- ​    Redis可视化： RedisDesktopManager
- ​    页面访问及调试： Google Chrome
- ​    markdown： Typora
- ​    截图： Snipaste

