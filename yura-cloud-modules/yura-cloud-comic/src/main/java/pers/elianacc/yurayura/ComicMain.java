package pers.elianacc.yurayura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import pers.elianacc.yurayura.config.MqttConfigProperties;

@SpringBootApplication
@MapperScan(basePackages = {"pers.elianacc.yurayura.dao"})
@EnableConfigurationProperties(value = MqttConfigProperties.class)
@EnableFeignClients
public class ComicMain {
    public static void main(String[] args) {
        SpringApplication.run(ComicMain.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  comic模块启动成功  ლ(´ڡ`ლ)ﾞ");
    }
}
