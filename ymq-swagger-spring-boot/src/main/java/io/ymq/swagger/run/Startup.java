package io.ymq.swagger.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:启动服务
 *
 * @author yanpenglei
 * @create 2017-10-26 16:37
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.swagger"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }
}
