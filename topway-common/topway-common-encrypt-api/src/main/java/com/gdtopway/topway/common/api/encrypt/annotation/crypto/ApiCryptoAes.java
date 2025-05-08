package com.gdtopway.topway.common.api.encrypt.annotation.crypto;

import com.gdtopway.topway.common.api.encrypt.annotation.decrypt.ApiDecrypt;
import com.gdtopway.topway.common.api.encrypt.annotation.encrypt.ApiEncrypt;
import com.gdtopway.topway.common.api.encrypt.enums.EncryptType;

import java.lang.annotation.*;

/**
 * AES加密解密注解
 *
 * @author Chill
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ApiEncrypt(EncryptType.AES)
@ApiDecrypt(EncryptType.AES)
public @interface ApiCryptoAes {

}
