package com.ztg.util;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.ztg.common.exception.CommonErrorCode;
import com.ztg.common.exception.CommonException;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Description: excel 导入导出工具类
 * @author: zhoutg
 * @time: 2020/6/2 19:18
 */
public class ExcelUtils {

    /**
     * 获取sheet信息，以<Sheet名称，序号>形式返回
     *
     * @param inputStream
     * @return
     */
    public static Map<String, Integer> getSheetsKeyString(InputStream inputStream) {
        Map<String, Integer> map = new LinkedMap<>();
        Workbook workbook = getWorkBook(inputStream);
        if (workbook == null) {
            return map;
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            map.put(workbook.getSheetName(i), i);
        }
        return map;
    }

    /**
     * 获取sheet信息，以<序号,Sheet名称>形式返回
     *
     * @param inputStream
     * @return
     */
    public static Map<Integer, String> getSheetsKeyInt(InputStream inputStream) {
        Map<Integer, String> map = new LinkedMap<>();
        Workbook workbook = getWorkBook(inputStream);
        if (workbook == null) {
            return map;
        }
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            map.put(i, workbook.getSheetName(i));
        }
        return map;
    }

    /**
     * 根据InputStream获取Workbook对象(兼容xls，xlsx)
     *
     * @param inputStream
     * @return
     */
    public static Workbook getWorkBook(InputStream inputStream) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 根据file获取Workbook对象(兼容xls，xlsx)
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(File file) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcelDynamicCol(List<ExcelExportEntity> entityList, Collection<?> dataSet, String title, String sheetName, String fileName,
                                             HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        dynamicColExport(entityList, dataSet, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response, float height) {
        Boolean havTitle = false;
        if (StringUtil.isNotBlank(title)) {
            havTitle = true;
        }
        userExport2(list, pojoClass, fileName, response, new ExportParams(title, sheetName), height, havTitle);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                      ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            ;
        }
        downLoadExcel(fileName, response, workbook);
    }

    private static void dynamicColExport(List<ExcelExportEntity> entityList, Collection<?> dataSet, String fileName, HttpServletResponse response,
                                         ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,entityList,dataSet);
        if (workbook != null) {
            ;
        }
        downLoadExcel(fileName, response, workbook);
    }

    private static void userExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                   ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            //列宽设置
            sheet.setColumnWidth(8, 256 * 20);
            sheet.setColumnWidth(9, 256 * 50);
            int rowNum = sheet.getLastRowNum();
            Row row = sheet.getRow(0);
            for (int i = 1; i <= rowNum; i++) {
                row = sheet.getRow(i);
                row.setHeightInPoints(12.8f);
            }
        }
        downLoadExcel(fileName, response, workbook);
    }

    private static void userExport2(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                    ExportParams exportParams, float height, Boolean havTitle) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            Row row = sheet.getRow(0);
            int startRowNum = 1;
            if (havTitle) {
                startRowNum = 2;
            }
            for (int i = startRowNum; i <= rowNum; i++) {
                row = sheet.getRow(i);
                row.setHeightInPoints(height);
            }
        }
        downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // throw new NormalException(e.getMessage());
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出Excel异常");
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            ;
        }
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("模板不能为空");
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            // throw new NormalException(e.getMessage());
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导入Excel异常");
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("excel文件不能为空");
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "excel文件不能为空");
        } catch (Exception e) {
            // throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导入Excel异常");
        }
        return list;
    }

    public static <T> List<T> importExcelMultiSheets(MultipartFile file, Integer titleRows, Integer headerRows, int sheetIndex,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setStartSheetIndex(sheetIndex);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("excel文件不能为空");
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "excel文件不能为空");
        } catch (Exception e) {
            // throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
            throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导入Excel异常");
        }
        return list;
    }



    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
     * <p>
     * sheetName     表格sheet名称
     * headersName  表格属性列名数组
     * headersId    表格属性列名对应的字段---你需要导出的字段名（为了更灵活控制你想要导出的字段）
     * dtoList     需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
     * out         与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * <p>
     * dtoRow      dtoList集合中每个对象记录所在行序号（从1开始，第0行是列标题栏）
     * zdCell      列标题字段所在单元格的列序号
     */
    public static void exportExcel(String sheetName, List<String> headersName, List<String> subheaders,
                                   List<String> colHeader, List<Map<String, Object>> dtoList) {
          /*
          （一）表头--标题栏
          */
        Map<Integer, String> headersNameMap = new HashMap<>();
        int key = 0;
        for (int i = 0; i < headersName.size(); i++) {
            if (!headersName.get(i).equals(null)) {
                headersNameMap.put(key, headersName.get(i));
                key++;
            }
        }
          /*
           （二）字段---标题对应的字段
          */
        Map<Integer, String> headersFieldMap = new HashMap<>();
        int value = 0;
        for (int i = 0; i < subheaders.size(); i++) {
            if (!subheaders.get(i).equals(null)) {
                headersFieldMap.put(value, subheaders.get(i));
                value++;
            }
        }
          /*
           (三）声明一个工作薄：包括构建工作簿、表格、样式
         */
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
//        HSSFSheet sheet = wb.
        sheet.setDefaultColumnWidth(7);
        sheet.setColumnWidth(0,5 * 256);
        sheet.setColumnWidth(1,40 * 256);
        sheet.setColumnWidth(3,20 * 256);
        sheet.setColumnWidth(7,50 * 256);
        sheet.setColumnWidth(8,10 * 256);

        // 生成一个样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        /*
         合并第一行单元格标题
        */
        HSSFRow row;
//        HSSFRow row = sheet.createRow(0);
        HSSFCell cell;
//        cell = row.createCell(0);
//        cell.setCellValue("学生基本信息表");
//        cell.setCellStyle(style);
        //CellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol)
//        CellRangeAddress cellra = new CellRangeAddress(0,0,0,headersNameMap.size()-1);
//        wb.getSheet(sheetName).addMergedRegion(cellra);

        //创建列标题栏所在行
        row = sheet.createRow(0);

        Collection c = headersNameMap.values();//拿到表格所有列标题的value的集合
        Iterator<String> it = c.iterator();//表格标题的迭代器
          /*
            （四）导出数据：包括导出标题栏以及内容栏
          */
        //根据选择的字段生成表头
        int size = 0;
        while (it.hasNext()) {
            cell = row.createCell(size);
            cell.setCellValue(it.next().toString());
            style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            cell.setCellStyle(style);
            size++;
        }

        row = sheet.createRow(1);

        c = headersFieldMap.values();//拿到表格所有列标题的value的集合
        it = c.iterator();//表格标题的迭代器
          /*
            （四）导出数据：包括导出标题栏以及内容栏
          */
        //根据选择的字段生成表头
        size = 0;
        while (it.hasNext()) {
            style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFillBackgroundColor(IndexedColors.SEA_GREEN.getIndex());

            cell = row.createCell(size);
            cell.setCellValue(it.next().toString());
            cell.setCellStyle(style);
            size++;
        }

        //表格列标题一行对应的字段的集合
        Collection headersFieldCo = headersFieldMap.values();
        Iterator<Map<String, Object>> labIt = dtoList.iterator();//总记录的迭代器
        int dtoRow = 2;//内容栏  导出数据dtoList的行序号
        while (labIt.hasNext()) {//记录的迭代器，遍历总记录
//            row = sheet.createRow(dtoRow);
//            dtoRow++;
//            T l = (T) labIt.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = l.getClass().getDeclaredFields();//获得JavaBean全部属性
            Map<String, Object> ln = labIt.next();
            dtoRow = Integer.parseInt(ln.get("idnum").toString())-1;
            if (dtoRow>1) {
                row = sheet.createRow(dtoRow);
                int zdCell = 0;
                for (String field : ln.keySet()) {
                    Object val = ln.get(field);
                    String textVal = "";
                    if (val != null) {
                        textVal = String.valueOf(val);//转化成String
                    } else {
                        //字段内容值为null时，进行处理
                        textVal = "";
                    }
                    zdCell = colHeader.indexOf(field);
                    if (zdCell >= 0) {
                        cell = row.createCell(zdCell);
                        cell.setCellValue(textVal);//写进excel对象
                    }
                }
            }
            /*
            for (short i = 0; i < fields.length; i++) {//遍历属性，比对
                Field field = fields[i];
                String fieldName = field.getName();//属性名
                Iterator<String> zdIt = headersFieldCo.iterator();//一行列标题字段的集合的迭代器
                int zdCell = 0;
                while (zdIt.hasNext()) {//遍历要导出的字段集合
                    if (zdIt.next().equals(fieldName)) {//比对JavaBean的属性名，一致就写入，不一致就丢弃
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);//拿到属性的get方法
                        Class tCls = l.getClass();//拿到JavaBean对象
                        try {
                            Method getMethod = tCls.getMethod(getMethodName,
                                    new Class[]{});//通过JavaBean对象拿到该属性的get方法，从而进行操控
                            Object val = getMethod.invoke(l, new Object[]{});//操控该对象属性的get方法，从而拿到属性值
                            String textVal = null;
                            if (val != null) {
                                textVal = String.valueOf(val);//转化成String
                            } else {
                                //字段内容值为null时，进行处理
                                textVal = "";
                            }
                            row.createCell(zdCell).setCellValue(textVal);//写进excel对象
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }//if
                    zdCell++;
                }//while
            }//for
            */
//            break;
        }

        //行列分组
        //wb.getSheet(sheetName).groupColumn(4, 7);
        try {
            FileOutputStream exportXls = new FileOutputStream("E://开单表.xls");
            wb.write(exportXls);
            exportXls.close();
            System.out.println("导出成功!");
        } catch (FileNotFoundException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("导出失败!");
            e.printStackTrace();
        }
    }
}
