package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.dto.RecordDetailDTO;
import com.ztg.dto.RecordDetailGroupDTO;
import com.ztg.dto.RecordDetailWinDTO;
import com.ztg.dto.RecordSettleDTO;
import com.ztg.dto.RecordTrendDTO;
import com.ztg.dto.RecordTrendPlayerDTO;
import com.ztg.entity.RecordDetail;
import com.ztg.entity.RecordPlayer;
import com.ztg.enums.IsDeleteEnum;
import com.ztg.service.RecordDetailService;
import com.ztg.service.impl.RecordDetailServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.util.EntityUtil;
import com.ztg.util.ExtUtil;
import com.ztg.util.ListUtil;
import com.ztg.vo.RecordDetailDelVO;
import com.ztg.vo.RecordDetailGetVO;
import com.ztg.vo.RecordDetailPageVO;
import com.ztg.vo.RecordDetailSaveVO;
import com.ztg.vo.RecordDetailVO;
import com.ztg.vo.RecordDetailWinVO;
import com.ztg.vo.RecordSettleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
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
        List<RecordDetailGroupDTO> records = recordDTOPageList.getRecords();
        // 获取赢家和金额
        if (ListUtil.isNotEmpty(records)) {
            Long recordId = records.get(0).getRecordId();
            List<Integer> groupNoList = records.stream().map(r -> r.getGroupNo()).collect(Collectors.toList());
            RecordDetailWinVO recordDetailWinVO = new RecordDetailWinVO();
            recordDetailWinVO.setRecordId(recordId);
            recordDetailWinVO.setGroupNoList(groupNoList);
            List<RecordDetailWinDTO> winPlayer = this.getWinPlayer(recordDetailWinVO);
            Map<Integer, RecordDetailWinDTO> groupNoMap = ExtUtil.getKeyObject(winPlayer, "groupNo");
            for (RecordDetailGroupDTO record : records) {
                RecordDetailWinDTO recordDetailWinDTO = groupNoMap.get(record.getGroupNo());
                if (recordDetailWinDTO != null) {
                    record.setPlayerName(recordDetailWinDTO.getPlayerName());
                    record.setMoney(recordDetailWinDTO.getMoney());
                }
            }
        }
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
        if (ListUtil.isEmpty(detailList)) {
            return Lists.newArrayList();
        }
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

    /**
     * 走势图
     *
     * @param recordSettleVO
     */
    public RecordTrendDTO trend(RecordSettleVO recordSettleVO) {
        RecordTrendDTO recordTrendDTO = new RecordTrendDTO();
        List<RecordTrendPlayerDTO> playerList = Lists.newArrayList();
        recordTrendDTO.setPlayerList(playerList);
        List<RecordDetail> detailList = this.list(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getRecordId, recordSettleVO.getRecordId())
        );
        if (ListUtil.isEmpty(detailList)) {
            return recordTrendDTO;
        }
        // 玩家走势数据
        Map<Long, List<BigDecimal>> trendMap = new LinkedHashMap<>();

        // 时间轴数据列表
        List<String> dateList = Lists.newArrayList();
        // 修改时间列表数据，用来获取时间轴数据
        List<String> modifiedList = Lists.newArrayList();
        for (RecordDetail recordDetail : detailList) {
            String modifiedDate = DateUtil.formatDateTime(recordDetail.getGmtModified());
            if (!modifiedList.contains(modifiedDate)) {
                modifiedList.add(modifiedDate);
                String modDate = DateUtil.format(recordDetail.getGmtModified(), "HH:mm");
                dateList.add(modDate);
            }
            if (trendMap.get(recordDetail.getPlayerId()) == null) {
                trendMap.put(recordDetail.getPlayerId(), new ArrayList<>());
            }
        }
        recordTrendDTO.setDateList(dateList);

        Map<Date, List<RecordDetail>> modifiedMap = ExtUtil.getKeyList(detailList, "gmtModified");
        int i = 0;
        for (Date date : modifiedMap.keySet()) {
            List<RecordDetail> recordDetails = modifiedMap.get(date);
            for (RecordDetail recordDetail : recordDetails) {
                List<BigDecimal> bigDecimalList = trendMap.get(recordDetail.getPlayerId());
                BigDecimal money = recordDetail.getMoney();
                if (ListUtil.isEmpty(bigDecimalList)) {
                    List<BigDecimal> bigList = new ArrayList<>();
                    bigList.add(money);
                    trendMap.put(recordDetail.getPlayerId(), bigList);
                } else {
                    bigDecimalList.add(bigDecimalList.get(bigDecimalList.size() - 1).add(money));
                }
            }
            i++;

            // 未参与的对当前数据赋值，值为最后一个数据
            for (Long aLong : trendMap.keySet()) {
                if (trendMap.get(aLong).size() < i) {
                    if (i == 1) {
                        trendMap.get(aLong).add(new BigDecimal(0));
                    } else {
                        trendMap.get(aLong).add(trendMap.get(aLong).get(i - 2));
                    }
                }
            }
        }

        // 输出结果
        List<RecordPlayer> playerNameList = recordPlayerFacade.list(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .in(RecordPlayer::getId, trendMap.keySet())
        );
        Map<Long, String> playerMap = ExtUtil.getKeyValue(playerNameList, "id", "name");
        for (Long aLong : trendMap.keySet()) {
            RecordTrendPlayerDTO recordTrendPlayerDTO = new RecordTrendPlayerDTO();
            recordTrendPlayerDTO.setPlayerId(aLong);
            recordTrendPlayerDTO.setName(playerMap.get((aLong)));
            recordTrendPlayerDTO.setMoneyList(trendMap.get(aLong));
            playerList.add(recordTrendPlayerDTO);
        }
        return recordTrendDTO;
    }

}
