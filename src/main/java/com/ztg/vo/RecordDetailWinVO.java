package com.ztg.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 明细
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDetailWinVO {

    // 场次id
    private Long recordId;
    // 分组
    private List<Integer> groupNoList;
}
