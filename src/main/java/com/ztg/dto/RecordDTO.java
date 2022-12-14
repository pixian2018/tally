package com.ztg.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 场次名称表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 场次名称
     */
    private String name;
}
