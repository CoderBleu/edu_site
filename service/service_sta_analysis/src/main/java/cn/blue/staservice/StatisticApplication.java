package cn.blue.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author Blue
 * EnableScheduling 开启定时任务
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.blue"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("cn.blue.staservice.mapper")
@EnableScheduling
public class StatisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }
}
