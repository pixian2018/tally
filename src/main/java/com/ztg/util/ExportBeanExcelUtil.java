// package com.ztg.tally.util;
//
// import com.diagbot.dto.RespDTO;
// import com.diagbot.exception.CommonErrorCode;
// import com.diagbot.exception.CommonException;
// import com.ztg.tally.common.RespDTO;
// import org.apache.poi.hssf.usermodel.HSSFCell;
// import org.apache.poi.hssf.usermodel.HSSFCellStyle;
// import org.apache.poi.hssf.usermodel.HSSFRow;
// import org.apache.poi.hssf.usermodel.HSSFSheet;
// import org.apache.poi.hssf.usermodel.HSSFWorkbook;
// import org.apache.poi.ss.usermodel.HorizontalAlignment;
//
// import javax.servlet.http.HttpServletResponse;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.lang.reflect.Field;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;
// import java.util.Collection;
// import java.util.HashMap;
// import java.util.Iterator;
// import java.util.List;
// import java.util.Map;
//
// @SuppressWarnings({ "deprecation" })
// public class ExportBeanExcelUtil<T> {
//     private final String MSG_SUCCESS = "操作成功!";
//     private final String MSG_ERROR = "操作失败!";
//
//     /**
//      * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
//      * <p>
//      * title         表格标题名
//      * headersName  表格属性列名数组
//      * headersId    表格属性列名对应的字段---你需要导出的字段名（为了更灵活控制你想要导出的字段）
//      * dtoList     需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
//      * out         与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//      */
//     public RespDTO exportExcel(String title, List<String> headersName, List<String> headersId,
//                                List<T> dtoList, String lujing, String fileName) {
//         /*（一）表头--标题栏*/
//         Map<Integer, String> headersNameMap = new HashMap<>();
//         int key = 0;
//         for (int i = 0; i < headersName.size(); i++) {
//             if (!headersName.get(i).equals(null)) {
//                 headersNameMap.put(key, headersName.get(i));
//                 key++;
//             }
//         }
//         /*（二）字段*/
//         Map<Integer, String> titleFieldMap = new HashMap<>();
//         int value = 0;
//         for (int i = 0; i < headersId.size(); i++) {
//             if (!headersId.get(i).equals(null)) {
//                 titleFieldMap.put(value, headersId.get(i));
//                 value++;
//             }
//         }
//         /* （三）声明一个工作薄：包括构建工作簿、表格、样式*/
//         HSSFWorkbook wb = new HSSFWorkbook();
//         HSSFSheet sheet = wb.createSheet(title);
//         sheet.setDefaultColumnWidth((short) 15);
//         // 生成一个样式
//         HSSFCellStyle style = wb.createCellStyle();
//         HSSFRow row = sheet.createRow(0);
//         style.setAlignment(HorizontalAlignment.CENTER);
//         HSSFCell cell;
//         Collection c = headersNameMap.values();//拿到表格所有标题的value的集合
//         Iterator<String> it = c.iterator();//表格标题的迭代器
//         /*（四）导出数据：包括导出标题栏以及内容栏*/
//         //根据选择的字段生成表头
//         short size = 0;
//         while (it.hasNext()) {
//             cell = row.createCell(size);
//             cell.setCellValue(it.next().toString());
//             cell.setCellStyle(style);
//             size++;
//         }
//         //表格标题一行的字段的集合
//         Collection zdC = titleFieldMap.values();
//         Iterator<T> labIt = dtoList.iterator();//总记录的迭代器
//         int zdRow = 0;//列序号
//         while (labIt.hasNext()) {//记录的迭代器，遍历总记录
//             int zdCell = 0;
//             zdRow++;
//             row = sheet.createRow(zdRow);
//             T l = (T) labIt.next();
//             // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//             Field[] fields = l.getClass().getDeclaredFields();//获得JavaBean全部属性
//             for (short i = 0; i < fields.length; i++) {//遍历属性，比对
//                 Field field = fields[i];
//                 String fieldName = field.getName();//属性名
//                 Iterator<String> zdIt = zdC.iterator();//一条字段的集合的迭代器
//                 while (zdIt.hasNext()) {//遍历要导出的字段集合
//                     if (zdIt.next().equals(fieldName)) {//比对JavaBean的属性名，一致就写入，不一致就丢弃
//                         String getMethodName = "get"
//                                 + fieldName.substring(0, 1).toUpperCase()
//                                 + fieldName.substring(1);//拿到属性的get方法
//                         Class tCls = l.getClass();//拿到JavaBean对象
//                         try {
//                             Method getMethod = tCls.getMethod(getMethodName,
//                                     new Class[] {});//通过JavaBean对象拿到该属性的get方法，从而进行操控
//                             Object val = getMethod.invoke(l, new Object[] {});//操控该对象属性的get方法，从而拿到属性值
//                             String textVal = null;
//                             if (val != null) {
//                                 textVal = String.valueOf(val);//转化成String
//                             } else {
//                                 textVal = null;
//                             }
//                             row.createCell((short) zdCell).setCellValue(textVal);//写进excel对象
//                             zdCell++;
//                         } catch (SecurityException e) {
//                             e.printStackTrace();
//                         } catch (IllegalArgumentException e) {
//                             e.printStackTrace();
//                         } catch (NoSuchMethodException e) {
//                             e.printStackTrace();
//                         } catch (IllegalAccessException e) {
//                             e.printStackTrace();
//                         } catch (InvocationTargetException e) {
//                             e.printStackTrace();
//                         }
//                     }
//                 }
//             }
//         }
//         try {
//             FileOutputStream exportXls = new FileOutputStream(lujing + fileName + ".xls");
//             wb.write(exportXls);
//             exportXls.close();
// /*		            resultMode.setResult(true);
// 		            resultMode.setMessage(MSG_SUCCESS);*/
//             return RespDTO.onSuc(MSG_SUCCESS);
//         } catch (FileNotFoundException e) {
// /*		            System.out.println("导出失败!");
// 		            resultMode.setResult(false);
// 			        resultMode.setMessage(MSG_ERROR);*/
//             e.printStackTrace();
//             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//         } catch (IOException e) {
//             e.printStackTrace();
//             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//         }
//
//         // return resultMode;
//     }
//
//     public void exportExcelNew(String title, List<String> headersName, List<String> headersId, List<T> dtoList,
//                                HttpServletResponse response) {
//         /* （一）表头--标题栏 */
//         Map<Integer, String> headersNameMap = new HashMap<>();
//         int key = 0;
//         for (int i = 0; i < headersName.size(); i++) {
//             if (!headersName.get(i).equals(null)) {
//                 headersNameMap.put(key, headersName.get(i));
//                 key++;
//             }
//         }
//         /* （二）字段 */
//         Map<Integer, String> titleFieldMap = new HashMap<>();
//         int value = 0;
//         for (int i = 0; i < headersId.size(); i++) {
//             if (!headersId.get(i).equals(null)) {
//                 titleFieldMap.put(value, headersId.get(i));
//                 value++;
//             }
//         }
//         /* （三）声明一个工作薄：包括构建工作簿、表格、样式 */
//         HSSFWorkbook wb = new HSSFWorkbook();
//         HSSFSheet sheet = wb.createSheet(title);
//         sheet.setDefaultColumnWidth((short) 15);
//         // 生成一个样式
//         HSSFCellStyle style = wb.createCellStyle();
//         HSSFRow row = sheet.createRow(0);
//         style.setAlignment(HorizontalAlignment.CENTER);
//         HSSFCell cell;
//         Collection c = headersNameMap.values();// 拿到表格所有标题的value的集合
//         Iterator<String> it = c.iterator();// 表格标题的迭代器
//         /* （四）导出数据：包括导出标题栏以及内容栏 */
//         // 根据选择的字段生成表头
//         short size = 0;
//         while (it.hasNext()) {
//             cell = row.createCell(size);
//             cell.setCellValue(it.next().toString());
//             cell.setCellStyle(style);
//             size++;
//         }
//         // 表格标题一行的字段的集合
//         Collection zdC = titleFieldMap.values();
//         Iterator<T> labIt = dtoList.iterator();// 总记录的迭代器
//         int zdRow = 0;// 列序号
//         while (labIt.hasNext()) {// 记录的迭代器，遍历总记录
//             int zdCell = 0;
//             zdRow++;
//             row = sheet.createRow(zdRow);
//             T l = (T) labIt.next();
//             // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//             Field[] fields = l.getClass().getDeclaredFields();// 获得JavaBean全部属性
//             for (short i = 0; i < fields.length; i++) {// 遍历属性，比对
//                 Field field = fields[i];
//                 String fieldName = field.getName();// 属性名
//                 Iterator<String> zdIt = zdC.iterator();// 一条字段的集合的迭代器
//                 while (zdIt.hasNext()) {// 遍历要导出的字段集合
//                     if (zdIt.next().equals(fieldName)) {// 比对JavaBean的属性名，一致就写入，不一致就丢弃
//                         String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 拿到属性的get方法
//                         Class tCls = l.getClass();// 拿到JavaBean对象
//                         try {
//                             Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// 通过JavaBean对象拿到该属性的get方法，从而进行操控
//                             Object val = getMethod.invoke(l, new Object[] {});// 操控该对象属性的get方法，从而拿到属性值
//                             String textVal = null;
//                             if (val != null) {
//                                 textVal = String.valueOf(val);// 转化成String
//                             } else {
//                                 textVal = null;
//                             }
//                             row.createCell((short) zdCell).setCellValue(textVal);// 写进excel对象
//                             zdCell++;
//                         } catch (SecurityException e) {
//                             e.printStackTrace();
//                             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//                         } catch (IllegalArgumentException e) {
//                             e.printStackTrace();
//                             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//                         } catch (NoSuchMethodException e) {
//                             e.printStackTrace();
//                             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//                         } catch (IllegalAccessException e) {
//                             e.printStackTrace();
//                             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//                         } catch (InvocationTargetException e) {
//                             e.printStackTrace();
//                             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//                         }
//                     }
//                 }
//             }
//         }
//         try {
//             String fileNameNew = String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
//             String headStr = "attachment; filename=\"" + fileNameNew + "\"";
//             response.reset();
//             response.setContentType("APPLICATION/OCTET-STREAM");
//             response.setHeader("Content-Disposition", headStr);
//             OutputStream out = response.getOutputStream();
//             wb.write(out);
//         } catch (IOException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//             throw new CommonException(CommonErrorCode.SERVER_IS_ERROR, "导出失败");
//         }
//
//     }
// }
