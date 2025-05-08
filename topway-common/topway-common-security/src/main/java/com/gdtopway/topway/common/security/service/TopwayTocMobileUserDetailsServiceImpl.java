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

package com.gdtopway.topway.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import com.gdtopway.topway.app.api.dto.AppUserInfo;
import com.gdtopway.topway.app.api.entity.AppUser;
import com.gdtopway.topway.app.api.feign.RemoteAppUserService;
import com.gdtopway.topway.common.core.constant.CommonConstants;
import com.gdtopway.topway.common.core.constant.SecurityConstants;
import com.gdtopway.topway.common.core.constant.enums.UserTypeEnum;
import com.gdtopway.topway.common.core.util.R;
import com.gdtopway.topway.common.core.util.RetOps;
import com.gdtopway.topway.common.core.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 *
 * @author lengleng hccake
 */
@Slf4j
@RequiredArgsConstructor
public class TopwayTocMobileUserDetailsServiceImpl implements TopwayUserDetailsService {

	private final RemoteAppUserService remoteAppUserService;

	/**
	 * 用户密码登录
	 * @param phone 用户密码登录
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		R<AppUserInfo> info = remoteAppUserService.social(phone, SecurityConstants.FROM_IN);
		return this.getUserDetailsAppUser(info);
	}

	@Override
	public UserDetails loadUserByUser(TopwayUser pigxUser) {
		return pigxUser;
	}

	UserDetails getUserDetailsAppUser(R<AppUserInfo> result) {
		// @formatter:off
		return RetOps.of(result).getData().map(this::convertUserDetailsAppUser).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
		// @formatter:on
	}

	/**
	 * UserInfo 转 UserDetails
	 * @param info
	 * @return 返回UserDetails对象
	 */
	UserDetails convertUserDetailsAppUser(AppUserInfo info) {
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(roleId -> dbAuthsSet.add(SecurityConstants.ROLE + roleId));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		Collection<? extends GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		AppUser user = info.getAppUser();
		// 构造security用户

		return new TopwayUser(user.getUserId(), user.getUsername(), null, user.getPhone(), user.getAvatar(),
				user.getNickname(), user.getName(), user.getEmail(), user.getTenantId(),
				SecurityConstants.BCRYPT + user.getPassword(), true, true, UserTypeEnum.TOC.getStatus(), true,
				!CommonConstants.STATUS_LOCK.equals(user.getLockFlag()), authorities);
	}

	@Override
	public int getOrder() {
		return 15;
	}

	/**
	 * 支持所有的 mobile 类型
	 * @param clientId 目标客户端
	 * @param grantType 授权类型
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		String header = WebUtils.getRequest().getHeader(SecurityConstants.HEADER_TOC);
		return SecurityConstants.HEADER_TOC_YES.equals(header) && SecurityConstants.GRANT_MOBILE.equals(grantType);
	}

}
