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

package com.gdtopway.topway.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdtopway.topway.admin.api.dto.UserDTO;
import com.gdtopway.topway.admin.api.dto.UserInfo;
import com.gdtopway.topway.admin.api.entity.SysUser;
import com.gdtopway.topway.admin.api.vo.UserExcelVO;
import com.gdtopway.topway.admin.api.vo.UserVO;
import com.gdtopway.topway.common.core.util.R;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * @author lengleng
 * @date 2017/10/31
 */
public interface SysUserService extends IService<SysUser> {

	/**
	 * 查询用户信息
	 * @param sysUser 用户
	 * @return userInfo
	 */
	UserInfo findUserInfo(SysUser sysUser);

	/**
	 * 分页查询用户信息（含有角色信息）
	 * @param page 分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	IPage getUsersWithRolePage(Page page, UserDTO userDTO);

	/**
	 * 删除用户
	 * @param ids 用户
	 * @return boolean
	 */
	Boolean deleteUserByIds(Long[] ids);

	/**
	 * 更新当前用户基本信息
	 * @param userDto 用户信息
	 * @return Boolean
	 */
	R<Boolean> updateUserInfo(UserDTO userDto);

	/**
	 * 更新指定用户信息
	 * @param userDto 用户信息
	 * @return
	 */
	Boolean updateUser(UserDTO userDto);

	/**
	 * 通过ID查询用户信息
	 * @param id 用户ID
	 * @return 用户信息
	 */
	UserVO selectUserVoById(Long id);

	/**
	 * 查询上级部门的用户信息
	 * @param username 用户名
	 * @return R
	 */
	List<SysUser> listAncestorUsers(String username);

	/**
	 * 保存用户信息
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	Boolean saveUser(UserDTO userDto);

	/**
	 * 查询全部的用户
	 * @param userDTO 查询条件
	 * @return list
	 */
	List<UserExcelVO> listUser(UserDTO userDTO);

	/**
	 * excel 导入用户
	 * @param excelVOList excel 列表数据
	 * @param bindingResult 错误数据
	 * @return ok fail
	 */
	R importUser(List<UserExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 注册用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	R<Boolean> registerUser(UserDTO userDto);

	/**
	 * 锁定用户
	 * @param username
	 * @return
	 */
	R<Boolean> lockUser(String username);

	R changePassword(UserDTO userDto);

	R unbinding(String type);

	R checkPassword(String password);

}
