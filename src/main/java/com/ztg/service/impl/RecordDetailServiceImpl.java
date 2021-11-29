package com.ztg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.mapper.RecordDetailMapper;
import com.ztg.service.RecordDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztg.vo.RecordDetailPageVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场次明细表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
@Service
public class RecordDetailServiceImpl extends ServiceImpl<RecordDetailMapper, RecordDetail> implements RecordDetailService {

    @Override
    public IPage<RecordDetailDTO> getPage(RecordDetailPageVO recordDetailPageVO) {
        return baseMapper.getPage(recordDetailPageVO);
    }
}
