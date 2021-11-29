package com.ztg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.vo.RecordPageVO;

/**
 * <p>
 * 场次名称表 Mapper 接口
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordMapper extends BaseMapper<Record> {

    public IPage<RecordDTO> getPage(RecordPageVO recordPageVO);
}
