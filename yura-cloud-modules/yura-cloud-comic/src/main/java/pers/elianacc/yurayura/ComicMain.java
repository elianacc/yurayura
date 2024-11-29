package pers.elianacc.yurayura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = {"pers.elianacc.yurayura.dao"})
@EnableFeignClients
public class ComicMain {
    public static void main(String[] args) {
        SpringApplication.run(ComicMain.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  comic模块启动成功  ლ(´ڡ`ლ)ﾞ");
    }
}
