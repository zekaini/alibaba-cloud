package com.gdtopway.topway.common.api.encrypt.annotation.encrypt;

import com.gdtopway.topway.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * rsa body 加密
 *
 * @author licoy.cn
 * @author L.cm
 * @version 2018/9/4
 * @see ApiEncrypt
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiEncrypt(EncryptType.RSA)
public @interface ApiEncryptRsa {

}
