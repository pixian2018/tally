package com.ztg.vo;

import lombok.Data;

/**
 * <p>
 * 场次明细表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDetailDelVO {

    // 场次id
    private Long recordId;
    // 分组
    private Integer groupNo;
}
