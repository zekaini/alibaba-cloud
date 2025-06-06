package com.gdtopway.topway.admin.api.feign;

import com.gdtopway.topway.common.core.constant.SecurityConstants;
import com.gdtopway.topway.common.core.constant.ServiceNameConstants;
import com.gdtopway.topway.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lengleng
 * @date 2020/5/12
 * <p>
 * 查询参数相关
 */
@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteParamService {

	/**
	 * 通过key 查询参数配置
	 * @param key key
	 * @param from 声明成内部调用，避免MQ 等无法调用
	 * @return
	 */
	@GetMapping("/param/publicValue/{key}")
	R<String> getByKey(@PathVariable("key") String key, @RequestHeader(SecurityConstants.FROM) String from);

}
