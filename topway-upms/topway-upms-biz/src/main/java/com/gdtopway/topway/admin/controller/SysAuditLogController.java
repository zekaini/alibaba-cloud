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
import com.gdtopway.topway.admin.api.entity.SysAuditLog;
import com.gdtopway.topway.admin.service.SysAuditLogService;
import com.gdtopway.topway.common.core.util.R;
import com.gdtopway.topway.common.excel.annotation.ResponseExcel;
import com.gdtopway.topway.common.log.annotation.SysLog;
import com.gdtopway.topway.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 审计记录表
 *
 * @author PIG
 * @date 2023-02-28 20:12:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
@Tag(description = "audit", name = "审计记录表管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysAuditLogController {

	private final SysAuditLogService sysAuditLogService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysAuditLog 审计记录表
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public R getsysAuditLogPage(@ParameterObject Page page, @ParameterObject SysAuditLog sysAuditLog) {
		return R.ok(sysAuditLogService.getAuditsByScope(page, sysAuditLog));
	}

	/**
	 * 通过id查询审计记录表
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(sysAuditLogService.getById(id));
	}

	/**
	 * 新增审计记录表 (异步插入)
	 * @param auditLogList 审计记录
	 * @return R
	 */
	@Inner
	@PostMapping
	@Operation(summary = "新增审计记录表", description = "新增审计记录表")
	public R save(@RequestBody List<SysAuditLog> auditLogList) {
		return R.ok(sysAuditLogService.saveBatch(auditLogList));
	}

	/**
	 * 通过id删除审计记录表
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除审计记录表", description = "通过id删除审计记录表")
	@SysLog("通过id删除审计记录表")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('sys_audit_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysAuditLogService.removeBatchByIds(CollUtil.toList(ids)));
	}

	/**
	 * 导出excel 表格
	 * @param sysAuditLog 查询条件
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_audit_export')")
	public List<SysAuditLog> export(SysAuditLog sysAuditLog) {
		return sysAuditLogService.list(Wrappers.query(sysAuditLog));
	}

}
