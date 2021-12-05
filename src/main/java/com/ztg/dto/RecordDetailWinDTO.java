package com.ztg.dto;

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
public class RecordDetailWinDTO {

    // 组号
    private Integer groupNo;
    // 玩家id
    private Long playerId;
    // 玩家名称
    private String playerName;
    // 金额
    private BigDecimal money;
}
