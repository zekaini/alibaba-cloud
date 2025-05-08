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

package com.gdtopway.topway.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdtopway.topway.admin.api.entity.SysSocialDetails;
import com.gdtopway.topway.admin.service.SysSocialDetailsService;
import com.gdtopway.topway.common.core.util.R;
import com.gdtopway.topway.common.core.util.ValidGroup;
import com.gdtopway.topway.common.log.annotation.SysLog;
import com.gdtopway.topway.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统社交登录账号表
 *
 * @author lengleng
 * @date 2018-08-16 21:30:41
 */
@RestController
@RequestMapping("/social")
@AllArgsConstructor
@Tag(description = "social", name = "三方账号管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysSocialDetailsController {

	private final SysSocialDetailsService sysSocialDetailsService;

	/**
	 * 社交登录账户简单分页查询
	 * @param page 分页对象
	 * @param sysSocialDetails 社交登录
	 * @return
	 */
	@GetMapping("/page")
	public R getSocialDetailsPage(@ParameterObject Page page, @ParameterObject SysSocialDetails sysSocialDetails) {
		return R.ok(sysSocialDetailsService.page(page, Wrappers.query(sysSocialDetails)));
	}

	/**
	 * 信息
	 * @param type 类型
	 * @return R
	 */
	@GetMapping("/{type}")
	public R getByType(@PathVariable("type") String type) {
		return R.ok(sysSocialDetailsService
				.list(Wrappers.<SysSocialDetails>lambdaQuery().eq(SysSocialDetails::getType, type)));
	}

	@GetMapping("/getById/{id}")
	public R info(@PathVariable("id") Long id) {
		return R.ok(sysSocialDetailsService.getById(id));
	}

	/**
	 * 保存
	 * @param sysSocialDetails
	 * @return R
	 */
	@SysLog("保存三方信息")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_social_details_add')")
	public R save(@Valid @RequestBody SysSocialDetails sysSocialDetails) {
		return R.ok(sysSocialDetailsService.save(sysSocialDetails));
	}

	/**
	 * 修改
	 * @param sysSocialDetails
	 * @return R
	 */
	@SysLog("修改三方信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_social_details_edit')")
	public R updateById(@Validated({ ValidGroup.Update.class }) @RequestBody SysSocialDetails sysSocialDetails) {
		sysSocialDetailsService.updateById(sysSocialDetails);
		return R.ok(Boolean.TRUE);
	}

	/**
	 * 删除
	 * @param ids id 列表
	 * @return R
	 */
	@SysLog("删除三方信息")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('sys_social_details_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysSocialDetailsService.removeBatchByIds(CollUtil.toList(ids)));
	}

	/**
	 * 通过社交账号、手机号查询用户、角色信息
	 * @param inStr appid@code
	 * @return
	 */
	@Inner
	@GetMapping("/info/{inStr}")
	public R getUserInfo(@PathVariable String inStr) {
		return R.ok(sysSocialDetailsService.getUserInfo(inStr));
	}

	/**
	 * 绑定社交账号
	 * @param state 类型
	 * @param code code
	 * @return
	 */
	@PostMapping("/bind")
	public R bindSocial(String state, String code) {
		return R.ok(sysSocialDetailsService.bindSocial(state, code));
	}

	/**
	 * 导出
	 */
	@GetMapping("/export")
	public List<SysSocialDetails> export(SysSocialDetails sysSocialDetails) {
		return sysSocialDetailsService.list(Wrappers.query(sysSocialDetails));
	}

}
