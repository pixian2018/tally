package com.ztg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.vo.RecordDetailPageVO;

/**
 * <p>
 * 场次明细表 Mapper 接口
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordDetailMapper extends BaseMapper<RecordDetail> {

    public IPage<RecordDetailDTO> getPage(RecordDetailPageVO recordDetailPageVO);
}
