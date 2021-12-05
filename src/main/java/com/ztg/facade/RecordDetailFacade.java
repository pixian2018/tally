package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.dto.RecordDetailGroupDTO;
import com.ztg.dto.RecordSettleDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.entity.RecordPlayer;
import com.ztg.enums.IsDeleteEnum;
import com.ztg.service.RecordDetailService;
import com.ztg.service.impl.RecordDetailServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.util.EntityUtil;
import com.ztg.util.ExtUtil;
import com.ztg.vo.RecordDetailDelVO;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailSaveVO;
import com.ztg.vo.RecordDetailVO;
import com.ztg.vo.RecordSettleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    @Autowired
    RecordPlayerFacade recordPlayerFacade;

    /**
     * 保存
     *
     * @param recordDetailSaveVO
     */
    public Boolean saveOrUpdate(RecordDetailSaveVO recordDetailSaveVO) {
        Date now = DateUtil.now();
        String user = recordDetailSaveVO.getUser();
        List<RecordDetailVO> recordDetailListVO = recordDetailSaveVO.getRecordDetailList();
        Map<Long, BigDecimal> moneyMap = recordDetailListVO.stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getMoney(), (r1, r2) -> r2));
        if (recordDetailSaveVO.getGroupNo() != null) { // 编辑只修改金额，不修改其他
            List<RecordDetail> list = this.list(new QueryWrapper<RecordDetail>().lambda()
                    .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                    .eq(RecordDetail::getRecordId, recordDetailSaveVO.getRecordId())
                    .eq(RecordDetail::getGroupNo, recordDetailSaveVO.getGroupNo())
            );
            for (RecordDetail recordDetail : list) {
                recordDetail.setMoney(moneyMap.get(recordDetail.getId()));
            }
            return recordDetailService.saveOrUpdateBatch(list);
        } else { // 新增
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
    }

    /**
     * 获取详情
     *
     * @param recordDetailGetVO
     * @return
     */
    public List<RecordDetailDTO> getByRecordAndGroupFac(RecordDetailGetVO recordDetailGetVO) {
        return this.getByRecordAndGroup(recordDetailGetVO);
    }

    /**
     * 分页
     *
     * @param recordDetailPageVO
     * @return
     */
    public IPage<RecordDetailGroupDTO> getPageFac(RecordDetailPageVO recordDetailPageVO) {
        IPage<RecordDetailGroupDTO> recordDTOPageList = getPage(recordDetailPageVO);
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

    /**
     * 结账
     *
     * @param recordSettleVO
     */
    public List<RecordSettleDTO> settle(RecordSettleVO recordSettleVO) {
        List<RecordDetail> detailList = this.list(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getRecordId, recordSettleVO.getRecordId())
        );
        Map<Long, List<RecordDetail>> detailMap = EntityUtil.makeEntityListMap(detailList, "playerId");
         List<RecordPlayer> playerList = recordPlayerFacade.list(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordPlayer::getRecordId, recordSettleVO.getRecordId())
                .in(RecordPlayer::getId, detailMap.keySet())
        );
        Map<Long, String> playerMap = ExtUtil.getKeyValue(playerList, "id", "name");
        List<RecordSettleDTO> recordSettleDTOList = Lists.newArrayList();
        for (Long id : detailMap.keySet()) {
            BigDecimal bigDecimal = new BigDecimal(0);
            List<RecordDetail> recordDetails = detailMap.get(id);
            for (RecordDetail recordDetail : recordDetails) {
                bigDecimal = bigDecimal.add(recordDetail.getMoney());
            }
            RecordSettleDTO recordSettleDTO = new RecordSettleDTO();
            recordSettleDTO.setMoney(bigDecimal);
            recordSettleDTO.setPlayerId(id);
            recordSettleDTO.setName(playerMap.get(id));
            recordSettleDTOList.add(recordSettleDTO);
        }
        return recordSettleDTOList;
    }

}
