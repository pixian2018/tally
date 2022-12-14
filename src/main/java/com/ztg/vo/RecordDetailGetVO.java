package com.ztg.vo;

import lombok.Data;

/**
 * <p>
 * 明细
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDetailGetVO {

    // 场次id
    private Long recordId;
    // 分组
    private Integer groupNo;
}
