package com.ztg.dto;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 结账走势图
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordTrendDTO {

    // 时间轴
    private List<String> dateList;
    // 玩家信息
    private List<RecordTrendPlayerDTO> playerList;
}
