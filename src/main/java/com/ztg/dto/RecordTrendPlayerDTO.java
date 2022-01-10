package com.ztg.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 走势图玩家信息
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordTrendPlayerDTO {

    // 玩家id
    private Long playerId;
    // 玩家名称
    private String name;
    // 结账金额
    private List<BigDecimal> moneyList;
}
