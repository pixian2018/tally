package com.ztg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 场次玩家表
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Data
public class RecordPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除,N:未删除，Y:删除
     */
    private String isDeleted;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 创建人，0表示无创建人值
     */
    private String creator;

    /**
     * 场次id
     */
    private Long recordId;

    /**
     * 玩家名称
     */
    private String name;
}
