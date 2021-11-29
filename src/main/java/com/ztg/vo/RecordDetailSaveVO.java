package com.ztg.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 场次名称表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDetailSaveVO {

    // 场次
    private Long recordId;
    // 用户
    private String user;
    // 明细
    private List<RecordDetailVO> recordDetailList;
}
