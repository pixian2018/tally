package com.ztg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 场次玩家表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Getter
@Setter
@TableName("record_player")
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
    @TableField("is_deleted")
    private String isDeleted;

    /**
     * 记录创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 记录修改时间,如果时间是1970年则表示纪录未修改
     */
    @TableField("gmt_modified")
    private Date gmtModified;

    /**
     * 创建人，0表示无创建人值
     */
    @TableField("creator")
    private String creator;

    /**
     * 修改人,如果为0则表示纪录未修改
     */
    @TableField("modifier")
    private String modifier;

    /**
     * 场次id
     */
    @TableField("record_id")
    private Long recordId;

    /**
     * 玩家名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序
     */
    @TableField("order_no")
    private Integer orderNo;

    /**
     * 是否加入（1：加入，0：未加入）
     */
    @TableField("is_join")
    private Integer isJoin;


}
