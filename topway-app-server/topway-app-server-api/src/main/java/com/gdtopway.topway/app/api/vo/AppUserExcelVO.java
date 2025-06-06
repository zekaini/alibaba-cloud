package com.gdtopway.topway.app.api.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.gdtopway.topway.common.excel.annotation.ExcelLine;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@ColumnWidth(30)
public class AppUserExcelVO {

	/**
	 * 导入时候回显行号
	 */
	@ExcelLine
	@ExcelIgnore
	private Long lineNum;

	/**
	 * 主键ID
	 */
	@ExcelProperty("用户编号")
	private Long userId;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	@ExcelProperty("用户名")
	private String username;

	/**
	 * 手机号
	 */
	@NotBlank(message = "手机号不能为空")
	@ExcelProperty("手机号")
	private String phone;

	/**
	 * 手机号
	 */
	@NotBlank(message = "昵称不能为空")
	@ExcelProperty("昵称")
	private String nickname;

	/**
	 * 手机号
	 */
	@NotBlank(message = "姓名不能为空")
	@ExcelProperty("姓名")
	private String name;

	/**
	 * 手机号
	 */
	@NotBlank(message = "邮箱不能为空")
	@ExcelProperty("邮箱")
	private String email;

	/**
	 * 角色列表
	 */
	@NotBlank(message = "角色不能为空")
	@ExcelProperty("角色")
	private String roleNameList;

	/**
	 * 锁定标记
	 */
	@ExcelProperty("锁定标记,0:正常,9:已锁定")
	private String lockFlag;

	/**
	 * 创建时间
	 */
	@ExcelProperty(value = "创建时间")
	private LocalDateTime createTime;

}
