# YuraYura



## 简介

> 本分支仅对网站B端接口部分(yurayura-business-service)，B端页面展示部分(yurayura-business-vue)，C端接口部分(未进行)，C端页面展示部分(未进行)项目源代码进行汇总存储，详情请访问以下地址
>
> B端接口部分—SpringBoot项目地址 https://github.com/elianacc/yurayura-business-service
>
> B端页面展示部分—Vue CLI 4项目地址 https://github.com/elianacc/yurayura-business-vue
>
> C端接口部分（暂无）
>
> C端页面展示部分（暂无）

## 部署

### Nginx部署B端、C端页面

#### 将Vue CLI4打包后的dist文件夹放入nginx目录的html目录下（也可放入任意位置），配置nginx（如下），监听80端口（也可用其他端口）

#### 通过配置实现

- nginx运行Vue CLI4打包静态资源
- nginx反向代理访问后端接口
- nginx负载均衡访问多个接口服务器及访问权重

nginx.conf

```
....

http {

    ....
	
    upstream yurayura-business-service {
      server 192.168.x.10 weight=1;
      server 192.168.x.11 weight=1;
      ....
    }
    
    server {
      listen       80;
      server_name  192.168.xx.xx;
      location / {
        root   x:/nginx-1.20.1/html/dist;
        index  index.html index.htm;
        try_files $uri $uri/ /index.html;
      }
      location /api/ {
        proxy_pass http://yurayura-business-service;
      }
      location /images/ {
        proxy_pass http://yurayura-business-service;
      }
      location /upload/ {
        proxy_pass http://yurayura-business-service;
      }
      error_page   500 502 503 504  /50x.html;
      location = /50x.html {
        root   x:/nginx-1.20.1/html;
      }
    }
    
    ....
   
}
```

### B端、C端接口部署

可以使用bat的方式运行SpringBoot打包后的jar，也可以直接java -jar的方式运行

