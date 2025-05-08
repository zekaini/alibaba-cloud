package com.gdtopway.topway.common.api.encrypt.annotation.decrypt;

import com.gdtopway.topway.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * @author licoy.cn
 * @version 2018/9/7
 * @see ApiDecrypt
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ApiDecrypt(EncryptType.RSA)
public @interface ApiDecryptRsa {

}
