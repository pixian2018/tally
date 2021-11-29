package com.ztg.vo;

import lombok.Data;

/**
 * <p>
 * 场次玩家表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordPlayerVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 玩家名称
     */
    private String name;

    /**
     * 是否加入（1：加入，0：未加入）
     */
    private Integer isJoin;

}
