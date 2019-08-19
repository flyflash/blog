package com.example.starterapp.utils;

import com.example.starterapp.constant.Constant;
import org.apache.tomcat.util.buf.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Base64;

public class DESUtil {

    public static String encrypt(String src) throws Exception {
        /* 生成key */
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
//        keyGenerator.init(56);
//        SecretKey secretKey = keyGenerator.generateKey();
//        byte[] keyEncoded = secretKey.getEncoded();

        /* 自定义key*/
        byte[] keyEncoded = Constant.DESKey.getBytes();

        /* 转换key*/
        DESKeySpec desKeySpec = new DESKeySpec(keyEncoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey generateSecret = factory.generateSecret(desKeySpec);

        /* 加密 */
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateSecret);
        byte[] result = cipher.doFinal(src.getBytes());

        return HexUtils.toHexString(result);
    }

    public static String decrypt(String result) throws Exception{
        /* 自定义key*/
        byte[] keyEncoded = Constant.DESKey.getBytes();

        /* 转换key*/
        DESKeySpec desKeySpec = new DESKeySpec(keyEncoded);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey generateSecret = factory.generateSecret(desKeySpec);

        /* 解密 */
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, generateSecret);
        byte[] bytes = cipher.doFinal(HexUtils.fromHexString(result));
        return new String(bytes);
    }

    /*public static void main(String[] args) {
        String sourceData = "test data encrypt";
        System.out.println("原始数据:" + sourceData);
        try {
            String encrypt = encrypt(sourceData);
            System.out.println("加密后的数据:" + encrypt);
            String decrypt = decrypt(encrypt);
            System.out.println("解密后的数据:" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
