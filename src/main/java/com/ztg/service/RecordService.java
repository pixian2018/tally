package com.ztg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.vo.RecordPageVO;

/**
 * <p>
 * 场次名称表 服务类
 * </p>
 *
 * @author zhoutg
 * @since 2021-11-29
 */
public interface RecordService extends IService<Record> {

    public IPage<RecordDTO> getPage(RecordPageVO recordPageVO);
}
