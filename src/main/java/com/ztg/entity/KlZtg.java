package com.ztg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 诊断依据信息表
 * </p>
 *
 * @author zhoutg
 * @since 2021-04-15
 */
public class KlZtg implements Serializable {

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
    private LocalDateTime gmtCreate;

    /**
     * 记录修改时间,如果时间是1970年则表示纪录未修改
     */
    private LocalDateTime gmtModified;

    /**
     * 创建人，0表示无创建人值
     */
    private String creator;

    /**
     * 修改人,如果为0则表示纪录未修改
     */
    private String modifier;

    /**
     * 疾病概念id
     */
    private Long conceptId;

    /**
     * 描述
     */
    private String description;

    /**
     * 启用状态（0：禁用，1：启用）
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    public Long getConceptId() {
        return conceptId;
    }

    public void setConceptId(Long conceptId) {
        this.conceptId = conceptId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "KlZtg{" +
            "id=" + id +
            ", isDeleted=" + isDeleted +
            ", gmtCreate=" + gmtCreate +
            ", gmtModified=" + gmtModified +
            ", creator=" + creator +
            ", modifier=" + modifier +
            ", conceptId=" + conceptId +
            ", description=" + description +
            ", status=" + status +
        "}";
    }
}
