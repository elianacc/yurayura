package pers.elianacc.yurayura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan(basePackages = {"pers.elianacc.yurayura.dao"})
@EnableFeignClients
public class UserMain {

    public static void main(String[] args) {
        SpringApplication.run(UserMain.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  user模块启动成功  ლ(´ڡ`ლ)ﾞ");
    }

}
