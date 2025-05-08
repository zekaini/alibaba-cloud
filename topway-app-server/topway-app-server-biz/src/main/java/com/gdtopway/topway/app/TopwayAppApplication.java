package com.gdtopway.topway.app;

import com.gdtopway.topway.common.feign.annotation.EnableTopwayFeignClients;
import com.gdtopway.topway.common.security.annotation.EnableTopwayResourceServer;
import com.gdtopway.topway.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author pigx archetype
 * <p>
 * 项目启动类
 */
@EnableOpenApi("app")
@EnableTopwayFeignClients
@EnableDiscoveryClient
@EnableTopwayResourceServer
@SpringBootApplication
public class TopwayAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopwayAppApplication.class, args);
	}

}
