package io.ymq.solr.run;

import io.ymq.solr.YmqRepository;
import io.ymq.solr.po.Ymq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-18 10:38
 **/
@SpringBootApplication
@ComponentScan(value = {"io.ymq.solr"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }


}
