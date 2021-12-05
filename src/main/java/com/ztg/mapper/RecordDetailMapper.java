package com.ztg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.dto.RecordDetailGroupDTO;
import com.ztg.dto.RecordDetailWinDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailWinVO;

import java.util.List;

/**
 * <p>
 * 场次明细表 Mapper 接口
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordDetailMapper extends BaseMapper<RecordDetail> {

    public IPage<RecordDetailGroupDTO> getPage(RecordDetailPageVO recordDetailPageVO);

    public List<RecordDetailDTO> getByRecordAndGroup(RecordDetailGetVO recordDetailGetVO);

    public List<RecordDetailWinDTO> getWinPlayer(RecordDetailWinVO recordDetailWinVO);
}
