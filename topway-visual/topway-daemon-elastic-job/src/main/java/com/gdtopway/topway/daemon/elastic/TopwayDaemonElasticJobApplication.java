/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.gdtopway.topway.daemon.elastic;

import com.gdtopway.topway.common.feign.annotation.EnableTopwayFeignClients;
import com.gdtopway.topway.common.job.annotation.EnableTopwayXxlJob;
import com.gdtopway.topway.common.security.annotation.EnableTopwayResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lengleng
 * @date 2018/7/24 分布式任务调度模块
 */
@EnableTopwayXxlJob
@EnableTopwayFeignClients
@EnableTopwayResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class TopwayDaemonElasticJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopwayDaemonElasticJobApplication.class, args);
	}

}
