package com.zephyr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@MapperScan("com.zephyr.item.core.dao")
@SpringBootApplication
public class DubboItemProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboItemProviderApplication.class, args);
	}

}
