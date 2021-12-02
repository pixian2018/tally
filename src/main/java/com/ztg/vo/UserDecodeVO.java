package com.ztg.vo;

import lombok.Data;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/12/1 16:35
 */
@Data
public class UserDecodeVO {

    private String encryptedData;
    private String sessionKey;
    private String iv;
}
