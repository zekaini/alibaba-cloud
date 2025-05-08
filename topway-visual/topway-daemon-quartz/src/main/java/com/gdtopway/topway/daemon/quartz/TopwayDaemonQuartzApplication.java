package com.gdtopway.topway.daemon.quartz;

import com.gdtopway.topway.common.feign.annotation.EnableTopwayFeignClients;
import com.gdtopway.topway.common.security.annotation.EnableTopwayResourceServer;
import com.gdtopway.topway.common.swagger.annotation.EnableOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author frwcloud
 * @date 2019/01/23 定时任务模块
 */
@EnableOpenApi("job")
@EnableTopwayFeignClients
@EnableTopwayResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class TopwayDaemonQuartzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopwayDaemonQuartzApplication.class, args);
	}

}
