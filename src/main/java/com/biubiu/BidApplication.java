package com.biubiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 张海彪
 * @create 2019-02-21 10:03
 */
@SpringBootApplication
@MapperScan(basePackages = "com.biubiu.mapper")
public class BidApplication {

    public static void main(String[] args) {
        SpringApplication.run(BidApplication.class);
    }

}
