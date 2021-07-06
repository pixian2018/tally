package com.ztg.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
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
public class RecordVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 场次名称
     */
    private String name;
}
