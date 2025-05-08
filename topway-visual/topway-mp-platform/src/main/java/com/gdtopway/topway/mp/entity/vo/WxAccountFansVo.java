package com.gdtopway.topway.mp.entity.vo;

import com.gdtopway.topway.mp.entity.WxAccountFans;
import com.gdtopway.topway.mp.entity.WxAccountTag;
import lombok.Data;

import java.util.List;

/**
 * 粉丝Vo 对象
 *
 * @author lengleng
 * @date 2022/1/5
 */
@Data
public class WxAccountFansVo extends WxAccountFans {

	/**
	 * 标签名称列表
	 */
	private List<WxAccountTag> tagList;

}
