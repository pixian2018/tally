package com.ztg.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 场次明细表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordDetailGroupDTO {

    // 修改时间
    private Date gmtModified;
    // 分组
    private Integer groupNo;
    // 场次id
    private Long recordId;
}
