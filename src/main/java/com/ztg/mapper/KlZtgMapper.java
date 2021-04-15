package com.ztg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ztg.entity.KlZtg;

import java.util.List;

/**
 * <p>
 * 诊断依据信息表 Mapper 接口
 * </p>
 *
 * @author zhoutg
 * @since 2021-04-15
 */
public interface KlZtgMapper extends BaseMapper<KlZtg> {

    public void insertBatch1(List<KlZtg> klZtgList);
}
