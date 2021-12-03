package com.ztg.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * 场次明细表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Getter
@Setter
public class RecordDetailVO {

    // 明细id
    private Long id;
    // 玩家id
    private Long playerId;
    // 金额
    private BigDecimal money;
}
