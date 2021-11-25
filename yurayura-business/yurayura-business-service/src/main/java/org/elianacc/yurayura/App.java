package org.elianacc.yurayura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync // 开启异步调用
@MapperScan(basePackages = {"org.elianacc.yurayura.dao"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
