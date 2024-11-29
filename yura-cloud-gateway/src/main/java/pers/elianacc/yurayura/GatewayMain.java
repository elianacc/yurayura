package pers.elianacc.yurayura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  网关模块启动成功  ლ(´ڡ`ლ)ﾞ");
    }

}
