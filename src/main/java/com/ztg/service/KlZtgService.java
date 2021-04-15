package com.ztg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ztg.entity.KlZtg;

import java.util.List;

/**
 * <p>
 * 诊断依据信息表 服务类
 * </p>
 *
 * @author zhoutg
 * @since 2021-04-15
 */
public interface KlZtgService extends IService<KlZtg> {

    public void insertBatch(List<KlZtg> klZtgList);
}
