package com.gdtopway.topway.admin.service;

import com.gdtopway.topway.common.core.util.R;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;

/**
 * @author lengleng
 * @date 2022/4/22
 * <p>
 * 互联平台
 */
public interface ConnectService {

	/**
	 * 同步钉钉部门
	 */
	Boolean syncDingDept();

	/**
	 * 同步钉钉用户
	 */
	R syncDingUser(Long deptId);

	/**
	 * 同步企微部门
	 * @return
	 */
	R<Boolean> syncCpDept();

	/**
	 * 同步企微用户
	 * @return
	 */
	R<Boolean> syncCpUser();

	WxCpDefaultConfigImpl getCpConfig();

}
