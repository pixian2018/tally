package com.ztg.vo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 场次玩家表
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Data
public class RecordPlayerSaveVO {

    // 用户
    private String user;
    // 场次id
    private Long recordId;
    // 玩家列表
    private List<RecordPlayerVO> recordPlayerVOList = Lists.newArrayList();

}
