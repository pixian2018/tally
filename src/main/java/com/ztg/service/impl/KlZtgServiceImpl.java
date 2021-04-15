package com.ztg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztg.entity.KlZtg;
import com.ztg.mapper.KlZtgMapper;
import com.ztg.service.KlZtgService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 诊断依据信息表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-04-15
 */
@Service
public class KlZtgServiceImpl extends ServiceImpl<KlZtgMapper, KlZtg> implements KlZtgService {

    @Override
    public void insertBatch(List<KlZtg> klZtgList) {
        baseMapper.insertBatch1(klZtgList);
    }
}
