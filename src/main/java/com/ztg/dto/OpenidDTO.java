package com.ztg.dto;

import lombok.Data;

/**
 * <p>
 * openid
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class OpenidDTO {

    // openid
    private String openid;
    // 玩家名称
    private String sessionKey;
}
