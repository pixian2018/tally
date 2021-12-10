package com.ztg.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import com.ztg.entity.RecordDetail;
import com.ztg.entity.RecordPlayer;
import com.ztg.enums.IsDeleteEnum;
import com.ztg.service.RecordPlayerService;
import com.ztg.service.impl.RecordPlayerServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.util.ListUtil;
import com.ztg.vo.RecordOrPlayerVO;
import com.ztg.vo.RecordPlayerDeleteVO;
import com.ztg.vo.RecordPlayerGetVO;
import com.ztg.vo.RecordPlayerSaveVO;
import com.ztg.vo.RecordPlayerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 场次玩家表 服务实现类
 * </p>
 *
 * @author zhoutg
 * @since 2021-07-05
 */
@Component
public class RecordPlayerFacade extends RecordPlayerServiceImpl {

    @Autowired
    @Qualifier("recordPlayerServiceImpl")
    RecordPlayerService recordPlayerService;
    @Autowired
    RecordDetailFacade recordDetailFacade;

    /**
     * 保存
     *
     * @param recordPlayerSaveVO
     */
    public Boolean saveOrUpdate(RecordPlayerSaveVO recordPlayerSaveVO) {
        List<RecordPlayerVO> recordPlayerVOList = recordPlayerSaveVO.getRecordPlayerVOList();
        if (ListUtil.isEmpty(recordPlayerVOList)) {
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "人员为空，请重新输入");
        }
        List<RecordPlayer> recordPlayerList = Lists.newArrayList();
        Date now = DateUtil.now();
        int i = 1;
        for (RecordPlayerVO recordPlayerVO : recordPlayerVOList) {
            RecordPlayer recordPlayer = new RecordPlayer();
            BeanUtil.copyProperties(recordPlayerVO, recordPlayer);
            recordPlayer.setRecordId(recordPlayerSaveVO.getRecordId());
            recordPlayer.setModifier(recordPlayerSaveVO.getUser());
            recordPlayer.setGmtModified(now);
            if (recordPlayerVO.getId() == null) {
                recordPlayer.setCreator(recordPlayerSaveVO.getUser());
                recordPlayer.setGmtCreate(now);
            }
            recordPlayer.setOrderNo(i++);
            recordPlayerList.add(recordPlayer);
        }
        return recordPlayerService.saveOrUpdateBatch(recordPlayerList);
    }

    /**
     * 获取列表
     *
     * @param recordPlayerGetVO
     * @return
     */
    public List<RecordPlayer> getList(RecordPlayerGetVO recordPlayerGetVO) {
        return this.list(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordPlayer::getRecordId, recordPlayerGetVO.getRecordId())
                .orderByAsc(RecordPlayer::getOrderNo)
        );
    }

    /**
     * 获取参与人员列表
     *
     * @param recordPlayerGetVO
     * @return
     */
    public List<RecordPlayer> getJoinList(RecordPlayerGetVO recordPlayerGetVO) {
        return this.list(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordPlayer::getRecordId, recordPlayerGetVO.getRecordId())
                .eq(RecordPlayer::getIsJoin, 1)
                .orderByAsc(RecordPlayer::getOrderNo)
        );
    }

    /**
     * 获取参与人员数量
     *
     * @param recordPlayerGetVO
     * @return
     */
    public Long getJoinCount(RecordPlayerGetVO recordPlayerGetVO) {
        return this.count(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordPlayer::getRecordId, recordPlayerGetVO.getRecordId())
                .eq(RecordPlayer::getIsJoin, 1)
        );
    }

    /**
     * 是否有记录或人员
     *
     * @param recordOrPlayerVO
     * @return
     */
    public Boolean hasRecordOrPlayer(RecordOrPlayerVO recordOrPlayerVO) {
        Long detailCount = recordDetailFacade.count(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getRecordId, recordOrPlayerVO.getRecordId())
        );
        if (detailCount > 0) {
            return true;
        }
        Long playerCount = this.count(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordPlayer::getRecordId, recordOrPlayerVO.getRecordId())
        );
        if (playerCount > 0) {
            return true;
        }
        return false;
    }




    /**
     * 删除
     *
     * @param recordPlayerDeleteVO
     */
    public Boolean delete(RecordPlayerDeleteVO recordPlayerDeleteVO) {
        long count = recordDetailFacade.count(new QueryWrapper<RecordDetail>().lambda()
                .eq(RecordDetail::getIsDeleted, IsDeleteEnum.N.getKey())
                .eq(RecordDetail::getPlayerId, recordPlayerDeleteVO.getId())
        );
        if (count > 0) {
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "已有记录，不能删除");
        }
        return this.remove(new QueryWrapper<RecordPlayer>().lambda()
                .eq(RecordPlayer::getId, recordPlayerDeleteVO.getId())
        );
    }

}
