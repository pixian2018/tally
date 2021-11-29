package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.dto.RecordDTO;
import com.ztg.entity.Record;
import com.ztg.entity.RecordDetail;
import com.ztg.entity.RecordPlayer;
import com.ztg.enums.IsDeleteEnum;
import com.ztg.service.impl.RecordServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.util.ListUtil;
import com.ztg.vo.RecordDeleteVO;
import com.ztg.vo.RecordGetVO;
import com.ztg.vo.RecordPageVO;
import com.ztg.vo.RecordSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


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

    @Autowired
    RecordDetailFacade recordDetailFacade;
    @Autowired
    RecordPlayerFacade recordPlayerFacade;

    /**
     * 保存
     *
     * @param recordSaveVO
     */
    public Boolean saveOrUpdate(RecordSaveVO recordSaveVO) {
        Date now = DateUtil.now();
        Record record = new Record();
        BeanUtil.copyProperties(recordSaveVO, record);
        record.setGmtModified(now);
        record.setModifier(recordSaveVO.getUser());
        if (recordSaveVO.getId() == null) { // 新增
            record.setGmtCreate(now);
            record.setCreator(recordSaveVO.getUser());
        }
        return this.saveOrUpdate(record);
    }

    /**
     * 获取详情
     *
     * @param recordGetVO
     * @return
     */
    public Record getById(RecordGetVO recordGetVO) {
        return this.getOne(new QueryWrapper<Record>().lambda()
                .eq(Record::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(Record::getId, recordGetVO.getId())
        );
    }


    /**
     * 分页
     *
     * @param recordPageVO
     * @return
     */
    public IPage<RecordDTO> getPageFac(RecordPageVO recordPageVO) {
        IPage<RecordDTO> recordDTOPage = getPage(recordPageVO);
        return recordDTOPage;
    }

    /**
     * 删除
     *
     * @param recordDeleteVO
     */
    public void delete(RecordDeleteVO recordDeleteVO) {
        if (ListUtil.isEmpty(recordDeleteVO.getIdList())) {
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "未选择数据");
        }
        // 删除明细
        recordDetailFacade.remove(new QueryWrapper<RecordDetail>().lambda().in(RecordDetail::getRecordId, recordDeleteVO.getIdList()));
        // 删除玩家
        recordPlayerFacade.remove(new QueryWrapper<RecordPlayer>().lambda().in(RecordPlayer::getRecordId, recordDeleteVO.getIdList()));
        // 删除场次
        this.remove(new QueryWrapper<Record>().lambda()
                .in(Record::getId, recordDeleteVO.getIdList()));
    }

}
