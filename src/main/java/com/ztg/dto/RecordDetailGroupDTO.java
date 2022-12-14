package com.ztg.dto;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 场次明细表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordDetailGroupDTO {

    // 修改时间
    @JsonFormat(pattern = "HH:mm:ss")
    private Date gmtModified;
    // 分组
    private Integer groupNo;
    // 场次id
    private Long recordId;
    // 玩家名称
    private String playerName;
    // 金额
    private BigDecimal money;
}
