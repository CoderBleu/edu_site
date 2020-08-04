package cn.blue.eduucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Blue
 * @date 2020/7/30
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"cn.blue"})
@MapperScan("cn.blue.eduucenter.mapper")
@EnableDiscoveryClient
public class EduCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduCenterApplication.class, args);
    }
}
