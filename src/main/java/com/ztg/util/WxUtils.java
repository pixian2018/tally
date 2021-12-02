package com.ztg.util;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/12/1 15:58
 */

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @author ljh
 */
public class WxUtils {
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = getUserInfo("jwNK5ab3BZ3OMGbx+HiNhgj5V0MG3gSvv69c+OHJy8JNkV8/cAmiRrP9opm9ZFDIrSg2CF89QWJlsRElzNzhLtFSU2oGVHT2Yq/Kgchoc5clzgYjpL6sBIjiQPznB/4POMQuW7pHaOJeVtF3NjpPeYXWV9POKDMEmhzzn0gfHOYkUQuXBZN8SnZQxnlRtSiHbNhuNOKYL2YrCZF3wUwh4FJqqCoLXw0oiU/GP1MUbXXwHOuLXSQRUj8ymso/shtr0UZmzCdaUTs0Ipbbp5Wbdl1pIf8uFrZLGYuVSOL6SS64M1OFBAYD8Og5pgRaQ0MzAOFUpjiZ23bXbL2J4haYtwzEmQ+Km7qnRc79L/pXPd0a88tewhiof/MRWpBOWKGDmDbN7PUXALG1H4zhlEZ48SJgVHBc/p+2jSvGM4C54B1mHmDUNhXrNuMAzGhZqrC29htWdqZj2GnLhsk63kk/1Q==",
                "4d+Q/xXZJzbDvs/OFt2LCQ==",
                "n2gxS1wuCR7tY7YIAHuYIg==");
        System.out.println(jsonObject);
    }

}