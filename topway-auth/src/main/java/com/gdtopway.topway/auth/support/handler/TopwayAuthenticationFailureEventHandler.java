/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gdtopway.topway.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.gdtopway.topway.admin.api.dto.SysLogDTO;
import com.gdtopway.topway.admin.api.feign.RemoteUserService;
import com.gdtopway.topway.common.core.constant.CacheConstants;
import com.gdtopway.topway.common.core.constant.CommonConstants;
import com.gdtopway.topway.common.core.constant.SecurityConstants;
import com.gdtopway.topway.common.core.util.KeyStrResolver;
import com.gdtopway.topway.common.core.util.MsgUtils;
import com.gdtopway.topway.common.core.util.R;
import com.gdtopway.topway.common.core.util.WebUtils;
import com.gdtopway.topway.common.data.resolver.ParamResolver;
import com.gdtopway.topway.common.log.event.SysLogEvent;
import com.gdtopway.topway.common.log.util.LogTypeEnum;
import com.gdtopway.topway.common.log.util.SysLogUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lengleng
 * @date 2022-06-02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TopwayAuthenticationFailureEventHandler implements AuthenticationFailureHandler {

	private static final MappingJackson2HttpMessageConverter errorHttpResponseConverter = new MappingJackson2HttpMessageConverter();

	private final RedisTemplate<String, String> redisTemplate;

	private final ApplicationEventPublisher publisher;

	private final KeyStrResolver tenantKeyStrResolver;

	private final RemoteUserService userService;

	/**
	 * Called when an authentication attempt fails.
	 * @param request the request during which the authentication attempt occurred.
	 * @param response the response.
	 * @param exception the exception which was thrown to reject the authentication
	 * request.
	 */
	@Override
	@SneakyThrows
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		log.info("登录失败，异常：{}", exception.getLocalizedMessage());

		// 密码模式记录错误信息
		if (OAuth2ParameterNames.PASSWORD.equals(grantType)) {
			String username = request.getParameter(OAuth2ParameterNames.USERNAME);

			// 密码错误记录错误次数
			if (exception instanceof OAuth2AuthenticationException) {
				recordLoginFailureTimes(username);
			}

			// 记录登录失败错误信息
			sendFailureEventLog(request, exception, username);
		}

		// 写出错误信息
		sendErrorResponse(request, response, exception);
	}

	/**
	 * 记录失败日志
	 * @param request HttpServletRequest
	 * @param exception 错误日志
	 * @param username 用户名
	 */
	private void sendFailureEventLog(HttpServletRequest request, AuthenticationException exception, String username) {
		SysLogDTO logVo = SysLogUtils.getSysLog();
		logVo.setTitle("登录失败");
		logVo.setLogType(LogTypeEnum.ERROR.getType());
		logVo.setException(exception.getLocalizedMessage());
		// 发送异步日志事件
		String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
		if (StrUtil.isNotBlank(startTimeStr)) {
			Long startTime = Long.parseLong(startTimeStr);
			Long endTime = System.currentTimeMillis();
			logVo.setTime(endTime - startTime);
		}
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		String clientId = WebUtils.extractClientId(header).orElse(null);
		logVo.setServiceId(clientId);
		logVo.setCreateBy(username);
		logVo.setTenantId(Long.parseLong(tenantKeyStrResolver.key()));
		publisher.publishEvent(new SysLogEvent(logVo));
	}

	/**
	 * 记录登录失败此处，如果操作阈值 调用接口锁定用户
	 * @param username username
	 */
	private void recordLoginFailureTimes(String username) {
		String key = String.format("%s:%s:%s", tenantKeyStrResolver.key(), CacheConstants.LOGIN_ERROR_TIMES, username);
		Long deltaTimes = ParamResolver.getLong("LOGIN_ERROR_TIMES", 5L);
		Long times = redisTemplate.opsForValue().increment(key);

		// 自动过期时间
		Long deltaTime = ParamResolver.getLong("DELTA_TIME", 1L);
		redisTemplate.expire(key, deltaTime, TimeUnit.HOURS);

		if (deltaTimes <= times) {
			userService.lockUser(username, SecurityConstants.FROM_IN);
		}
	}

	private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
		httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
		String errorMessage;

		if (exception instanceof OAuth2AuthenticationException) {
			OAuth2AuthenticationException authorizationException = (OAuth2AuthenticationException) exception;
			errorMessage = StrUtil.isBlank(authorizationException.getError().getDescription())
					? authorizationException.getError().getErrorCode()
					: authorizationException.getError().getDescription();
		}
		else {
			errorMessage = exception.getLocalizedMessage();
		}

		// 手机号登录
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (SecurityConstants.APP.equals(grantType)) {
			errorMessage = MsgUtils.getSecurityMessage("AbstractUserDetailsAuthenticationProvider.smsBadCredentials");
		}

		this.errorHttpResponseConverter.write(R.failed(errorMessage), MediaType.APPLICATION_JSON, httpResponse);
	}

}
