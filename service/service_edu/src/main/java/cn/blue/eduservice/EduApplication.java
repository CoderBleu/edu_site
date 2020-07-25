package cn.blue.eduservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author coderblue
 * EnableTransactionManagement 事务管理器
 */
@SpringBootApplication
@ComponentScan(basePackages = {"cn.blue"})
@EnableTransactionManagement
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
