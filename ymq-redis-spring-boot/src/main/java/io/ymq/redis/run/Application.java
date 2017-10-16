package io.ymq.redis.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-16 13:19
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.redis"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
