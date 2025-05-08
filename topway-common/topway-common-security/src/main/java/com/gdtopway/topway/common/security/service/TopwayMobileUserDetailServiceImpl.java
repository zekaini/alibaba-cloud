package com.gdtopway.topway.common.security.service;

import com.gdtopway.topway.admin.api.dto.UserInfo;
import com.gdtopway.topway.admin.api.feign.RemoteUserService;
import com.gdtopway.topway.common.core.constant.SecurityConstants;
import com.gdtopway.topway.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author aeizzz
 */
@Slf4j
@RequiredArgsConstructor
public class TopwayMobileUserDetailServiceImpl implements TopwayUserDetailsService {

	private final UserDetailsService pigxDefaultUserDetailsServiceImpl;

	private final RemoteUserService remoteUserService;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		R<UserInfo> result = remoteUserService.social(phone, SecurityConstants.FROM_IN);
		return getUserDetails(result);
	}

	@Override
	public UserDetails loadUserByUser(TopwayUser pigxUser) {
		return pigxDefaultUserDetailsServiceImpl.loadUserByUsername(pigxUser.getUsername());
	}

	/**
	 * 支持所有的 mobile 类型
	 * @param clientId 目标客户端
	 * @param grantType 授权类型
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.GRANT_MOBILE.equals(grantType);
	}

}
