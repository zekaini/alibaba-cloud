package com.gdtopway.topway.common.api.encrypt.annotation.decrypt;

import com.gdtopway.topway.common.api.encrypt.enums.EncryptType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * aes 界面
 *
 * @author licoy.cn
 * @author L.cm
 * @version 2018/9/7
 * @see ApiDecrypt
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(EncryptType.AES)
public @interface ApiDecryptAes {

	/**
	 * Alias for {@link ApiDecrypt#secretKey()}.
	 * @return {String}
	 */
	@AliasFor(annotation = ApiDecrypt.class)
	String secretKey() default "";

}
