package com.gdtopway.topway.admin.api.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.gdtopway.topway.common.excel.annotation.ExcelLine;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 部门导入导出
 */
@Data
public class DeptExcelVo implements Serializable {

	/**
	 * 导入时候回显行号
	 */
	@ExcelLine
	@ExcelIgnore
	private Long lineNum;

	/**
	 * 上级部门
	 */
	@NotBlank(message = "上级部门不能为空")
	@ExcelProperty("上级部门")
	private String parentName;

	/**
	 * 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@ExcelProperty("部门名称")
	private String name;

	/**
	 * 排序
	 */
	@ExcelProperty(value = "排序值")
	private Integer sortOrder;

}
