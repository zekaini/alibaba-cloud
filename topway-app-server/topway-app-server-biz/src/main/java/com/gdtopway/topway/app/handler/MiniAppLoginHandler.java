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

package com.gdtopway.topway.app.handler;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gdtopway.topway.app.api.dto.AppUserInfo;
import com.gdtopway.topway.app.api.entity.AppSocialDetails;
import com.gdtopway.topway.app.api.entity.AppUser;
import com.gdtopway.topway.app.mapper.AppSocialDetailsMapper;
import com.gdtopway.topway.app.service.AppUserService;
import com.gdtopway.topway.common.core.constant.SecurityConstants;
import com.gdtopway.topway.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019年11月02日
 * <p>
 * 微信小程序
 */
@Slf4j
@Component("APP-MINI")
@AllArgsConstructor
public class MiniAppLoginHandler extends AbstractLoginHandler {

	private final AppUserService appUserService;

	private final AppSocialDetailsMapper appSocialDetailsMapper;

	/**
	 * 小程序登录传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		AppSocialDetails condition = new AppSocialDetails();
		condition.setType(LoginTypeEnum.MINI_APP.getType());
		AppSocialDetails socialDetails = appSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		String url = String.format(SecurityConstants.MINI_APP_AUTHORIZATION_CODE_URL, socialDetails.getAppId(),
				socialDetails.getAppSecret(), code);
		String result = HttpUtil.get(url);
		log.debug("微信小程序响应报文:{}", result);

		Object obj = JSONUtil.parseObj(result).get("openid");
		return obj.toString();
	}

	/**
	 * openId 获取用户信息
	 * @param openId
	 * @return
	 */
	@Override
	public AppUserInfo info(String openId) {
		AppUser user = appUserService.getOne(Wrappers.<AppUser>query().lambda().eq(AppUser::getWxOpenid, openId));

		if (user == null) {
			log.info("微信小程序未绑定:{},创建新的用户", openId);
			return createAndSaveAppUserInfo(openId);
		}
		return appUserService.findUserInfo(user);
	}

	/**
	 * 绑定逻辑
	 * @param user 用户实体
	 * @param identify 渠道返回唯一标识
	 * @return
	 */
	@Override
	public Boolean bind(AppUser user, String identify) {
		user.setWxOpenid(identify);
		appUserService.updateById(user);
		return true;
	}

	private AppUserInfo createAndSaveAppUserInfo(String openId) {
		AppUser appUser = new AppUser();
		appUser.setWxOpenid(openId);
		appUser.setUsername(openId);
		appUserService.saveOrUpdate(appUser, Wrappers.<AppUser>lambdaQuery().eq(AppUser::getUsername, openId));

		AppUserInfo appUserDTO = new AppUserInfo();
		appUserDTO.setAppUser(appUser);
		return appUserDTO;
	}

}
