package com.ztg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * 场次明细表 服务类
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordDetailService extends IService<RecordDetail> {

    public IPage<RecordDetailGroupDTO> getPage(RecordDetailPageVO recordDetailPageVO);

    public List<RecordDetailDTO> getByRecordAndGroup(RecordDetailGetVO recordDetailGetVO);

    public List<RecordDetailWinDTO> getWinPlayer(RecordDetailWinVO recordDetailWinVO);
}
