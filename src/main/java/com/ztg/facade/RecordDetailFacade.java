package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.enums.IsDeleteEnum;
import com.ztg.service.RecordDetailService;
import com.ztg.service.impl.RecordDetailServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.vo.RecordDetailDelVO;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailSaveVO;
import com.ztg.vo.RecordDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 场次明细表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Component
public class RecordDetailFacade extends RecordDetailServiceImpl {

    @Autowired
    @Qualifier("recordDetailServiceImpl")
    RecordDetailService recordDetailService;

    /**
     * 保存
     *
     * @param recordDetailSaveVO
     */
    public Boolean saveOrUpdate(RecordDetailSaveVO recordDetailSaveVO) {
        Date now = DateUtil.now();
        String user = recordDetailSaveVO.getUser();
        RecordDetail one = this.getOne(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getRecordId, recordDetailSaveVO.getRecordId())
                .orderByDesc(RecordDetail::getId), false
        );
        int group = 1;
        if (one != null) {
            group = one.getGroupNo() + 1;
        }
        List<RecordDetail> recordDetailList = Lists.newArrayList();
        for (RecordDetailVO recordDetailVO : recordDetailSaveVO.getRecordDetailList()) {
            RecordDetail recordDetail = new RecordDetail();
            BeanUtil.copyProperties(recordDetailVO, recordDetail);
            recordDetail.setGroupNo(group);
            recordDetail.setCreator(user);
            recordDetail.setModifier(user);
            recordDetail.setGmtCreate(now);
            recordDetail.setGmtModified(now);
            recordDetail.setRecordId(recordDetailSaveVO.getRecordId());
            recordDetailList.add(recordDetail);
        }
        return recordDetailService.saveBatch(recordDetailList);
    }

    /**
     * 获取详情
     *
     * @param recordDetailGetVO
     * @return
     */
    public RecordDetail getById(RecordDetailGetVO recordDetailGetVO) {
        return this.getOne(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getId, recordDetailGetVO.getId())
        );
    }


    /**
     * 分页
     *
     * @param recordDetailPageVO
     * @return
     */
    public IPage<RecordDetailDTO> getPageFac(RecordDetailPageVO recordDetailPageVO) {
        IPage<RecordDetailDTO> recordDTOPageList = getPage(recordDetailPageVO);
        return recordDTOPageList;
    }

    /**
     * 删除
     *
     * @param recordDetailDelVO
     */
    public Boolean delete(RecordDetailDelVO recordDetailDelVO) {
        if (recordDetailDelVO.getRecordId() == null || recordDetailDelVO.getGroupNo() == null) {
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "未选择数据");
        }
        return this.remove(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getRecordId, recordDetailDelVO.getRecordId())
                .eq(RecordDetail::getGroupNo, recordDetailDelVO.getGroupNo())
        );
    }

}
