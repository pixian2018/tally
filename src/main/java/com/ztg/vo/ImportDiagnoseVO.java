package com.ztg.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 诊断依据导入
 * </p>
 *
 * @author zhaops
 * @since 2020-07-28
 */
@Data
public class ImportDiagnoseVO implements IExcelDataModel, Serializable {

    private static final long serialVersionUID = 1L;

    @Excel(name="类型")
    private String type = "";
    @Excel(name="序号")
    private String orderNo = "";
    @Excel(name="规则")
    private String rule = "";
    @Excel(name="等于")
    private String eq;
    @Excel(name="最大值")
    private String max;
    @Excel(name="最大值符号")
    private String maxSymbol;
    @Excel(name="单位")
    private String maxUnit;
    @Excel(name="最小值")
    private String min;
    @Excel(name="最小值符号")
    private String minSymbol;
    @Excel(name="单位")
    private String minUnit;
    //行号
    private Integer rowNum;

    // 规则标准词id
    private Long conceptId;

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer integer) {
        this.rowNum = rowNum + 1;
    }
}
