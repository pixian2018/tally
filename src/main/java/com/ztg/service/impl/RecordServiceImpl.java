package com.ztg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.mapper.RecordMapper;
import com.ztg.service.RecordService;
import com.ztg.vo.RecordPageVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 场次名称表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Override
    public IPage<RecordDTO> getPage(RecordPageVO recordPageVO) {
        return baseMapper.getPage(recordPageVO);
    }
}
