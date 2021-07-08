package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.service.impl.RecordServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.util.ListUtil;
import com.ztg.vo.RecordDeleteVO;
import com.ztg.vo.RecordPageVO;
import com.ztg.vo.RecordVO;
import org.springframework.stereotype.Component;


/**
 * <p>
 * 场次名称表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Component
public class RecordFacade extends RecordServiceImpl{

    public void saveOrUpdate(RecordVO recordVO) {
        Record record = new Record();
        BeanUtil.copyProperties(recordVO, record);
        record.setGmtCreate(DateUtil.now());
        this.saveOrUpdate(record);
    }

    public IPage<RecordDTO> getPageFac(RecordPageVO recordPageVO) {
        IPage<RecordDTO> recordDTOPage = getPage(recordPageVO);
        return recordDTOPage;
    }

    public void delete(RecordDeleteVO recordDeleteVO) {
        if (ListUtil.isEmpty(recordDeleteVO.getIdList())) {
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "未选择数据");
        }
        this.remove(new QueryWrapper<Record>()
                .eq("creator", recordDeleteVO.getCreator())
                .in("id", recordDeleteVO.getIdList()));
    }

}
