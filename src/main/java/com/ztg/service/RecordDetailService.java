package com.ztg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.vo.RecordDetailPageVO;

/**
 * <p>
 * 场次明细表 服务类
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordDetailService extends IService<RecordDetail> {

    public IPage<RecordDetailDTO> getPage(RecordDetailPageVO recordDetailPageVO);
}
