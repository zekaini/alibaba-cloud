/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gdtopway.topway.common.xss;

import com.gdtopway.topway.common.xss.config.TopwayXssProperties;
import com.gdtopway.topway.common.xss.core.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * jackson xss 配置
 *
 * @author L.cm
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(TopwayXssProperties.class)
@ConditionalOnProperty(prefix = TopwayXssProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
public class TopwayXssAutoConfiguration implements WebMvcConfigurer {

	private final TopwayXssProperties xssProperties;

	@Bean
	@ConditionalOnMissingBean
	public XssCleaner xssCleaner(TopwayXssProperties properties) {
		return new DefaultXssCleaner(properties);
	}

	@Bean
	@ConditionalOnMissingBean
	public FormXssClean formXssClean(TopwayXssProperties properties, XssCleaner xssCleaner) {
		return new FormXssClean(properties, xssCleaner);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(TopwayXssProperties properties,
			XssCleaner xssCleaner) {
		JacksonXssClean xssClean = new JacksonXssClean(properties, xssCleaner);
		return builder -> builder.deserializerByType(String.class, xssClean);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> patterns = xssProperties.getPathPatterns();
		if (patterns.isEmpty()) {
			patterns.add("/**");
		}
		XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
		registry.addInterceptor(interceptor).addPathPatterns(patterns)
				.excludePathPatterns(xssProperties.getPathExcludePatterns()).order(Ordered.LOWEST_PRECEDENCE);
	}

}
