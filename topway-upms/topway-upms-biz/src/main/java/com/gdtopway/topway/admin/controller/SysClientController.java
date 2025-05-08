/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.gdtopway.topway.admin.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdtopway.topway.admin.api.dto.SysOauthClientDetailsDTO;
import com.gdtopway.topway.admin.api.entity.SysOauthClientDetails;
import com.gdtopway.topway.admin.config.ClientDetailsInitRunner;
import com.gdtopway.topway.admin.service.SysOauthClientDetailsService;
import com.gdtopway.topway.common.core.constant.CommonConstants;
import com.gdtopway.topway.common.core.util.R;
import com.gdtopway.topway.common.core.util.SpringContextHolder;
import com.gdtopway.topway.common.excel.annotation.ResponseExcel;
import com.gdtopway.topway.common.log.annotation.SysLog;
import com.gdtopway.topway.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Tag(description = "client", name = "客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysClientController {

	private final SysOauthClientDetailsService clientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId clientId
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public R getByClientId(@PathVariable String clientId) {
		SysOauthClientDetails details = clientDetailsService
				.getOne(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId));
		String information = details.getAdditionalInformation();
		String captchaFlag = JSONUtil.parseObj(information).getStr(CommonConstants.CAPTCHA_FLAG);
		String encFlag = JSONUtil.parseObj(information).getStr(CommonConstants.ENC_FLAG);
		String onlineQuantity = JSONUtil.parseObj(information).getStr(CommonConstants.ONLINE_QUANTITY);
		SysOauthClientDetailsDTO dto = new SysOauthClientDetailsDTO();
		BeanUtils.copyProperties(details, dto);
		dto.setCaptchaFlag(captchaFlag);
		dto.setEncFlag(encFlag);
		dto.setOnlineQuantity(onlineQuantity);
		return R.ok(dto);
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public R getOauthClientDetailsPage(@ParameterObject Page page,
			@ParameterObject SysOauthClientDetails sysOauthClientDetails) {
		LambdaQueryWrapper<SysOauthClientDetails> wrapper = Wrappers.<SysOauthClientDetails>lambdaQuery()
				.like(StrUtil.isNotBlank(sysOauthClientDetails.getClientId()), SysOauthClientDetails::getClientId,
						sysOauthClientDetails.getClientId())
				.like(StrUtil.isNotBlank(sysOauthClientDetails.getClientSecret()),
						SysOauthClientDetails::getClientSecret, sysOauthClientDetails.getClientSecret());
		return R.ok(clientDetailsService.page(page, wrapper));
	}

	/**
	 * 添加
	 * @param clientDetailsDTO 实体
	 * @return success/false
	 */
	@SysLog("添加终端")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public R add(@Valid @RequestBody SysOauthClientDetailsDTO clientDetailsDTO) {
		return R.ok(clientDetailsService.saveClient(clientDetailsDTO));
	}

	/**
	 * 删除
	 * @param ids ID 列表
	 * @return success/false
	 */
	@SysLog("删除终端")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public R removeById(@RequestBody Long[] ids) {
		clientDetailsService.removeBatchByIds(CollUtil.toList(ids));
		SpringContextHolder.publishEvent(new ClientDetailsInitRunner.ClientDetailsInitEvent(ids));
		return R.ok();
	}

	/**
	 * 编辑
	 * @param clientDetailsDTO 实体
	 * @return success/false
	 */
	@SysLog("编辑终端")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public R update(@Valid @RequestBody SysOauthClientDetailsDTO clientDetailsDTO) {
		return R.ok(clientDetailsService.updateClientById(clientDetailsDTO));
	}

	@Inner(false)
	@GetMapping("/getClientDetailsById/{clientId}")
	public R getClientDetailsById(@PathVariable String clientId) {
		return R.ok(clientDetailsService.getOne(
				Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId), false));
	}

	/**
	 * 查询全部客户端
	 * @return
	 */
	@Inner(false)
	@GetMapping("/list")
	public R listClients() {
		return R.ok(clientDetailsService.list());
	}

	/**
	 * 同步缓存字典
	 * @return R
	 */
	@SysLog("同步终端")
	@PutMapping("/sync")
	public R sync() {
		return clientDetailsService.syncClientCache();
	}

	/**
	 * 导出所有客户端
	 * @return excel
	 */
	@ResponseExcel
	@SysLog("导出excel")
	@GetMapping("/export")
	public List<SysOauthClientDetails> export(SysOauthClientDetails sysOauthClientDetails) {
		return clientDetailsService.list(Wrappers.query(sysOauthClientDetails));
	}

}
