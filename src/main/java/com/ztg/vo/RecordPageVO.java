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
public class RecordPageVO extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场次名称
     */
    private String name;

    /**
     * 创建人
     */
    private String creator;
}
