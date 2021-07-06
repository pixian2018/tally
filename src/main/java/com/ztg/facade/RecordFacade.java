package com.ztg.facade;

import com.ztg.entity.Record;
import com.ztg.service.RecordService;
import com.ztg.service.impl.RecordServiceImpl;
import com.ztg.util.BeanUtil;
import com.ztg.util.DateUtil;
import com.ztg.vo.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RecordFacade {

    public void saveEntity(RecordVO recordVO) {
        Record record = new Record();
        BeanUtil.copyProperties(recordVO, record);
        record.setGmtCreate(DateUtil.now());
        // recordService.saveOrUpdate(record);
    }

}
