package com.ztg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.dto.RecordDetailGroupDTO;
import com.ztg.dto.RecordDetailWinDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.mapper.RecordDetailMapper;
import com.ztg.service.RecordDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailWinVO;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public IPage<RecordDetailGroupDTO> getPage(RecordDetailPageVO recordDetailPageVO) {
        return baseMapper.getPage(recordDetailPageVO);
    }

    @Override
    public List<RecordDetailDTO> getByRecordAndGroup(RecordDetailGetVO recordDetailGetVO) {
        return baseMapper.getByRecordAndGroup(recordDetailGetVO);
    }

    @Override
    public List<RecordDetailWinDTO> getWinPlayer(RecordDetailWinVO recordDetailWinVO) {
        return baseMapper.getWinPlayer(recordDetailWinVO);
    }
}
