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

package com.gdtopway.topway.common.api.encrypt.core;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdtopway.topway.common.api.encrypt.annotation.decrypt.ApiDecrypt;
import com.gdtopway.topway.common.api.encrypt.bean.CryptoInfoBean;
import com.gdtopway.topway.common.api.encrypt.config.ApiEncryptProperties;
import com.gdtopway.topway.common.api.encrypt.util.ApiCryptoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;

/**
 * param 参数 解析
 *
 * @author L.cm
 */
@RequiredArgsConstructor
public class ApiDecryptParamResolver implements HandlerMethodArgumentResolver {

	private final ApiEncryptProperties properties;

	private final ObjectMapper objectMapper;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return AnnotatedElementUtils.hasAnnotation(parameter.getParameter(), ApiDecrypt.class);
	}

	@Nullable
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Parameter parameter = methodParameter.getParameter();
		ApiDecrypt apiDecrypt = AnnotatedElementUtils.getMergedAnnotation(parameter, ApiDecrypt.class);
		String text = webRequest.getParameter(properties.getParamName());
		if (StrUtil.isBlank(text)) {
			return null;
		}
		CryptoInfoBean infoBean = new CryptoInfoBean(apiDecrypt.value(), apiDecrypt.secretKey());
		byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
		byte[] decryptData = ApiCryptoUtil.decryptData(textBytes, infoBean);

		return objectMapper.readValue(decryptData, parameter.getType());
	}

}
