package com.ztg.vo;

import lombok.Data;

/**
 * <p>
 * 场次名称表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordSaveVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户
     */
    private String user;

    /**
     * 场次名称
     */
    private String name;
}
