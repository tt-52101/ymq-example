package io.ymq.rabbitmq.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

/**
 * 描述:  启动服务
 * author: yanpenglei
 * Date: 2017/10/16 16:58
 */
@SpringBootApplication
@ComponentScan(value = {"io.ymq.rabbitmq"})
public class Startup {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Startup.class, args);
    }
}
