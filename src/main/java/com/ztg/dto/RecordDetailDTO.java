package com.ztg.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 场次明细表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordDetailDTO {


    /**
     *
     */
    // 主键
    private Long id;

    /**
     * 场次id
     */
    @TableField("record_id")
    private Long recordId;

    /**
     * 玩家id
     */
    @TableField("player_id")
    private Long playerId;

    /**
     * 金额
     */
    @TableField("money")
    private BigDecimal money;

}
