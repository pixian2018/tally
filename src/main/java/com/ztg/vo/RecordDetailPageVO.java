package com.ztg.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 场次名称表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDetailPageVO extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    // 场次
    private Long recordId;

}
