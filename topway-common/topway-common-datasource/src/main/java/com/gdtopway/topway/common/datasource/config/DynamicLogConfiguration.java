package com.gdtopway.topway.common.datasource.config;

import com.gdtopway.topway.common.core.factory.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.PropertySource;

/**
 * @author lengleng
 * @date 2022/8/8
 *
 * 注入SQL 格式化的插件
 */
@ConditionalOnClass(name = "com.gdtopway.topway.common.data.mybatis.DruidSqlLogFilter")
@PropertySource(value = "classpath:dynamic-ds-log.yaml", factory = YamlPropertySourceFactory.class)
public class DynamicLogConfiguration {

}
