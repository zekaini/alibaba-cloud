package com.gdtopway.topway.common.audit;

import com.gdtopway.topway.admin.api.feign.RemoteAuditLogService;
import com.gdtopway.topway.common.audit.aop.AuditAspect;
import com.gdtopway.topway.common.audit.handle.DefaultAuditLogHandle;
import com.gdtopway.topway.common.audit.handle.IAuditLogHandle;
import com.gdtopway.topway.common.audit.handle.ICompareHandle;
import com.gdtopway.topway.common.audit.handle.JavesCompareHandle;
import com.gdtopway.topway.common.audit.support.SpelParser;
import com.gdtopway.topway.common.core.util.KeyStrResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Optional;

/**
 * 审计自动配置类
 *
 * @author lengleng
 * @date 2023/2/26
 */
@EnableAsync
@AutoConfiguration
@Import({ AuditAspect.class, SpelParser.class })
public class AuditAutoConfiguration {

	/**
	 * 默认注入 javers 的比较器实现
	 * @param auditNameHandleOptional 注入审计用户来源
	 * @return ICompareHandle
	 */
	@Bean
	@ConditionalOnMissingBean
	public ICompareHandle compareHandle(Optional<IAuditLogHandle> auditNameHandleOptional) {
		return new JavesCompareHandle(auditNameHandleOptional);
	}

	/**
	 * 默认的审计日志存储策略
	 * @return DefaultAuditLogHandle
	 */
	@Bean
	@ConditionalOnMissingBean
	public IAuditLogHandle auditLogHandle(RemoteAuditLogService logService, KeyStrResolver tenantKeyStrResolver) {
		return new DefaultAuditLogHandle(logService, tenantKeyStrResolver);
	}

}
