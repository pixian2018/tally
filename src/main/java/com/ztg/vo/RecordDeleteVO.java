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
public class RecordDeleteVO {

    // id列表
    private List<Long> idList;

    // 创建人
    private String creator;
}
