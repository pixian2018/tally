package com.ztg.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/12/1 16:35
 */
@Data
public class UserDecodeVO {

    @NotBlank(message = "登录凭证[code]为空")
    private String code;
}
