package com.ztg.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @description: 非对称加密算法，公钥加密，私钥解密
 * @author: zhoutg
 * @time: 2019/10/29 10:00
 */
public class RSAEncrypt {

    // 公钥
    private static final String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCkoEsfWLLa3Y8hlxG7QB9Rew7iMJ3b7Dev3euMckA0F1Ewq3v4nBHQyX75ZOn2blMU1qm46OPJAE05Pgt2MMYOhdvMR5jDNQSrynMx/0lMkY24u+nfM+kMuAIxQOHwSQN7N2dJY1v1vn4fAChL5PiJIpy6Ub5r9HIF6nUWTso2FwIDAQAB";
    // 密钥
    private static final String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKSgSx9YstrdjyGXEbtAH1F7DuIwndvsN6/d64xyQDQXUTCre/icEdDJfvlk6fZuUxTWqbjo48kATTk+C3Ywxg6F28xHmMM1BKvKczH/SUyRjbi76d8z6Qy4AjFA4fBJA3s3Z0ljW/W+fh8AKEvk+IkinLpRvmv0cgXqdRZOyjYXAgMBAAECgYBwJhrHIf/GBgWJ1oZnz8kGExUSlhgUSBmvnzjmFrcSRYgz5b6woHGqkHoFn++ax5Glso8wvsbemWwCQPhB7fwdsD6dn4YD+KidxAQCR3V4kqt8yxsG2ZyiPjeGtFqAUZHRQF0FxXl40FEjGbCymy2HnR+tDXYn9KinWNN1z1zjMQJBAPaKanseVGKZ43oufGad9rBeb/WCQNOwsInDRSAGC5xEmJ8AD0RApTI3G2AUj8K6jSg/OYh332qNFk/KKcpUrzsCQQCq8UrfmPO+WzHuHXnFWZpXvY8scq9+cPeIXNNbsb6Zw+0lFFvJ52LLdHE1bQjSKQz4RUsKaxbfU8wIj5Gvqp7VAkEA6mHjI1NNREQq+ABzshcg7MGLTOtc+CP1YbMr52r35MNuGVM0MF4I7zlmpCxIFAfIpOeeqbfUxfaFrKt0mW9y+wJBAIIKjasL8GEffxxlz/0R7RhSXFiz5k2KIsTZ2PAoh5byNbW/hxRRshaUvoqmJ8+46uTz0eJbFUtPtCfPEEVSxwECQQDZNVxuvdUNeImElaSIbF8NS7hnvMF8mNzgOQwOoXJ2gB9+BNQebiZiJC17oL29hPOCfvDS+4Hmt5UtOfjU9Zf1";

    public static void main(String[] args) throws Exception {
        String text = "我爱中华人民共和国";
        String en = RSAEncrypt.encrypt(text);
        System.out.println(en);
        System.out.println("解密文件：");
        System.out.println(RSAEncrypt.decrypt(en));

        String text1 = RSAEncrypt.encrypt("我爱北京", privateKey);
        System.out.println(text1);
        System.out.println(RSAEncrypt.decrypt(text1, pubKey));

       //生成公钥和私钥
       // genKeyPair();
    }


    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        // 得到公钥字符串
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        System.out.println("公钥:" + publicKeyString);
        System.out.println("私钥:" + privateKeyString);
    }


    /**
     * RSA公钥加密
     *
     * @param str       需要加密的字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        // 加密时超过117字节就报错。为此采用分段加密的办法来加密
        byte[] data = str.getBytes("UTF-8");
        byte[] dataReturn = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i += 100) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i,
                    i + 100));
            sb.append(new String(doFinal));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        String outStr = Base64.encodeBase64String(dataReturn);
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);

        // 解密时超过128字节就报错。为此采用分段解密的办法来解密
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputByte.length; i += 128) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(inputByte, i,
                    i + 128));
            sb.append(new String(doFinal));
        }
        return sb.toString();
    }


    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception {
        return decrypt(str, privateKey);
    }

    /**
     * RSA公钥加密
     *
     * @param str 需要加密的字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception {
        return encrypt(str, pubKey);
    }

    /**
     * 将密文解密，转成Map
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static Map<String, String> convertMap(String str) throws Exception {
        Map<String, String> map = new LinkedHashMap<>();
        String strDe = decrypt(str, privateKey);
        String[] item = strDe.split("\\$\\$");
        for (String s : item) {
            String[] keyVal = s.split("=");
            map.put(keyVal[0], keyVal[1]);
        }
        return map;
    }
}
