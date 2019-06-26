package com.zephyr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@MapperScan("com.zephyr.order.core.dao")
@SpringBootApplication
public class DubboOrderProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboOrderProviderApplication.class, args);
	}

}
