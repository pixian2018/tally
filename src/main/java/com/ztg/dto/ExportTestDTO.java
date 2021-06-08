package com.ztg.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/6/7 14:24
 */
@Data
public class ExportTestDTO {

    @Excel(name = "名称")
    private String name;

    @Excel(name = "地址")
    private String addr;
}
