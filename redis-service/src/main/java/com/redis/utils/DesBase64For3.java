package com.redis.utils;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Security;

/**
 * 描述：des加密算法
 *
 * @author 2015-1-31
 * @version 1.1.0
 * @since 1.1.0
 */
@SuppressWarnings("restriction")
public final class DesBase64For3 {
    /**
     * 工具类
     */
    private DesBase64For3() {
    }

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    /**  */
    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";
    /**  */
    private static final String ENCODING = "utf-8";
    /**  */
    private static final String MCRYPT_TRIPLEDES = "DESede";
    /**  */
    private static final String DES_APP_KEY = "ljypzswlljypzswlljypzswl";
    /**  */
    private static final String IV = "12345678";

    /**
     * 加密
     *
     * @param encryptStr 已加密String
     * @return String
     * @throws Exception cert
     */
    public static String decrypt(final String encryptStr) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(DES_APP_KEY.getBytes(ENCODING));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MCRYPT_TRIPLEDES);
        SecretKey sec = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParameters = new IvParameterSpec(IV.getBytes(ENCODING));
        cipher.init(Cipher.DECRYPT_MODE, sec, ivParameters);
        return new String(cipher.doFinal(Base64.decode(encryptStr)), ENCODING);
    }

    /**
     * 解密
     *
     * @param plainText 已加密String
     * @return String
     * @throws Exception cert
     */
    public static String encrypt(final String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(DES_APP_KEY.getBytes(ENCODING));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MCRYPT_TRIPLEDES);
        SecretKey sec = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivParameters = new IvParameterSpec(IV.getBytes(ENCODING));
        cipher.init(Cipher.ENCRYPT_MODE, sec, ivParameters);
        return Base64.encode(cipher.doFinal(plainText.getBytes(ENCODING)));
    }

}