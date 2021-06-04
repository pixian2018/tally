package com.ztg.util;

import com.google.common.collect.Lists;
import com.ztg.annotation.Ex;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zhoutg
 * @time: 2021/5/19 16:34
 */
public class ExcelBigUtil {

    /**
     * The type of the data value is indicated by an attribute on the cell. The
     * value is usually in a "v" element within the cell.
     */
    enum xssfDataType {
        BOOL, ERROR, FORMULA, INLINESTR, SSTINDEX, NUMBER,
    }

    /**
     * 使用xssf_sax_API处理Excel,请参考： http://poi.apache.org/spreadsheet/how-to.html#xssf_sax_api
     * <p/>
     * Also see Standard ECMA-376, 1st edition, part 4, pages 1928ff, at
     * http://www.ecma-international.org/publications/standards/Ecma-376.htm
     * <p/>
     * A web-friendly version is http://openiso.org/Ecma/376/Part4
     */
    class MyXSSFSheetHandler extends DefaultHandler {

        /**
         * Table with styles
         */
        private StylesTable stylesTable;

        /**
         * Table with unique strings
         */
        private ReadOnlySharedStringsTable sharedStringsTable;

        /**
         * Destination for data
         */
        private final PrintStream output;

        /**
         * Number of columns to read starting with leftmost
         */
        private final int minColumnCount;

        // Set when V start element is seen
        private boolean vIsOpen;

        // Set when cell start element is seen;
        // used when cell close element is seen.
        private xssfDataType nextDataType;

        // Used to format numeric cell values.
        private short formatIndex;
        private String formatString;
        private final DataFormatter formatter;

        private int thisColumn = -1;
        // The last column printed to the output stream
        private int lastColumnNumber = -1;

        // Gathers characters as they are seen.
        private StringBuffer value;
        private String[] record;
        private List<String[]> rows = new ArrayList<String[]>();
        private boolean isCellNull = false;

        /**
         * Accepts objects needed while parsing.
         *
         * @param styles  Table of styles
         * @param strings Table of shared strings
         * @param cols    Minimum number of columns to show
         * @param target  Sink for output
         */
        public MyXSSFSheetHandler(StylesTable styles,
                                  ReadOnlySharedStringsTable strings, int cols, PrintStream target) {
            this.stylesTable = styles;
            this.sharedStringsTable = strings;
            this.minColumnCount = cols;
            this.output = target;
            this.value = new StringBuffer();
            this.nextDataType = xssfDataType.NUMBER;
            this.formatter = new DataFormatter();
            record = new String[this.minColumnCount];
            rows.clear();// 每次读取都清空行集合
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
         * java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {

            if ("inlineStr".equals(name) || "v".equals(name)) {
                vIsOpen = true;
                // Clear contents cache
                value.setLength(0);
            }
            // c => cell
            else if ("c".equals(name)) {
                // Get the cell reference
                String r = attributes.getValue("r");
                int firstDigit = -1;
                for (int c = 0; c < r.length(); ++c) {
                    if (Character.isDigit(r.charAt(c))) {
                        firstDigit = c;
                        break;
                    }
                }
                thisColumn = nameToColumn(r.substring(0, firstDigit));

                // Set up defaults.
                this.nextDataType = xssfDataType.NUMBER;
                this.formatIndex = -1;
                this.formatString = null;
                String cellType = attributes.getValue("t");
                String cellStyleStr = attributes.getValue("s");
                if ("b".equals(cellType)) {
                    nextDataType = xssfDataType.BOOL;
                } else if ("e".equals(cellType)) {
                    nextDataType = xssfDataType.ERROR;
                } else if ("inlineStr".equals(cellType)) {
                    nextDataType = xssfDataType.INLINESTR;
                } else if ("s".equals(cellType)) {
                    nextDataType = xssfDataType.SSTINDEX;
                } else if ("str".equals(cellType)) {
                    nextDataType = xssfDataType.FORMULA;
                } else if (cellStyleStr != null) {
                    // It's a number, but almost certainly one
                    // with a special style or format
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    this.formatIndex = style.getDataFormat();
                    this.formatString = style.getDataFormatString();
                    if (this.formatString == null) {
                        this.formatString = BuiltinFormats
                                .getBuiltinFormat(this.formatIndex);
                    }
                }
            }

        }

        /*
         * (non-Javadoc)
         *
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
         * java.lang.String, java.lang.String)
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            String thisStr = null;

            // v => contents of a cell
            if ("v".equals(name)) {
                // Process the value contents as required.
                // Do now, as characters() may be called more than once
                switch (nextDataType) {

                    case BOOL:
                        char first = value.charAt(0);
                        thisStr = first == '0' ? "FALSE" : "TRUE";
                        break;

                    case ERROR:
                        thisStr = "ERROR:" + value.toString();
                        break;

                    case FORMULA:
                        // A formula could result in a string value,
                        // so always add double-quote characters.
                        thisStr = value.toString();
                        break;

                    case INLINESTR:
                        // TODO: have seen an example of this, so it's untested.
                        XSSFRichTextString rtsi = new XSSFRichTextString(
                                value.toString());
                        thisStr = rtsi.toString();
                        break;

                    case SSTINDEX:
                        String sstIndex = value.toString();
                        try {
                            int idx = Integer.parseInt(sstIndex);
                            XSSFRichTextString rtss = new XSSFRichTextString(
                                    sharedStringsTable.getEntryAt(idx));
                            thisStr = rtss.toString();
                        } catch (NumberFormatException ex) {
                            output.println("Failed to parse SST index '" + sstIndex
                                    + "': " + ex.toString());
                        }
                        break;

                    case NUMBER:
                        String n = value.toString();
                        // 判断是否是日期格式
                        if (HSSFDateUtil.isADateFormat(this.formatIndex, n)) {
                            Double d = Double.parseDouble(n);
                            Date date = HSSFDateUtil.getJavaDate(d);
                            thisStr = formateDateToString(date);
                        } else if (this.formatString != null) {
                            thisStr = formatter.formatRawCellContents(
                                    Double.parseDouble(n), this.formatIndex,
                                    this.formatString);
                        } else {
                            thisStr = n;
                        }
                        break;

                    default:
                        thisStr = "(TODO: Unexpected type: " + nextDataType + ")";
                        break;
                }

                // Output after we've seen the string contents
                // Emit commas for any fields that were missing on this row
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
                //判断单元格的值是否为空
                if (thisStr == null || "".equals(isCellNull)) {
                    isCellNull = true;// 设置单元格是否为空值
                }
                // trim()
                if (thisStr != null) {
                    thisStr = thisStr.trim();
                }
                record[thisColumn] = thisStr;
                // Update column
                if (thisColumn > -1) {
                    lastColumnNumber = thisColumn;
                }

            } else if ("row".equals(name)) {
                // Print out any missing commas if needed
                if (minColumns > 0) {
                    // Columns are 0 based
                    if (lastColumnNumber == -1) {
                        lastColumnNumber = 0;
                    }
                    if (isCellNull == false && record[0] != null
                            && record[1] != null)// 判断是否空行
                    {
                        rows.add(record.clone());
                        isCellNull = false;
                        for (int i = 0; i < record.length; i++) {
                            record[i] = null;
                        }
                    }
                }
                lastColumnNumber = -1;
            }

        }

        public List<String[]> getRows() {
            return rows;
        }

        public void setRows(List<String[]> rows) {
            this.rows = rows;
        }

        /**
         * Captures characters only if a suitable element is open. Originally
         * was just "v"; extended for inlineStr also.
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            if (vIsOpen) {
                value.append(ch, start, length);
            }
        }

        /**
         * Converts an Excel column name like "C" to a zero-based index.
         *
         * @param name
         * @return Index corresponding to the specified name
         */
        private int nameToColumn(String name) {
            int column = -1;
            for (int i = 0; i < name.length(); ++i) {
                int c = name.charAt(i);
                column = (column + 1) * 26 + c - 'A';
            }
            return column;
        }

        private String formateDateToString(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化日期
            return sdf.format(date);

        }

    }

    // /

    private OPCPackage xlsxPackage;
    private int minColumns;
    private PrintStream output;
    private String sheetName;

    /**
     * Creates a new XLSX -> CSV converter
     *
     * @param pkg        The XLSX package to process
     * @param output     The PrintStream to output the CSV to
     * @param minColumns The minimum number of columns to output, or -1 for no minimum
     */
    public ExcelBigUtil(OPCPackage pkg, PrintStream output,
                        String sheetName, int minColumns) {
        this.xlsxPackage = pkg;
        this.output = output;
        this.minColumns = minColumns;
        this.sheetName = sheetName;
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and
     * shared-strings tables.
     *
     * @param styles
     * @param strings
     * @param sheetInputStream
     */
    public List<String[]> processSheet(StylesTable styles,
                                       ReadOnlySharedStringsTable strings, InputStream sheetInputStream)
            throws IOException, ParserConfigurationException, SAXException {

        InputSource sheetSource = new InputSource(sheetInputStream);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        XMLReader sheetParser = saxParser.getXMLReader();
        MyXSSFSheetHandler handler = new MyXSSFSheetHandler(styles, strings,
                this.minColumns, this.output);
        sheetParser.setContentHandler(handler);
        sheetParser.parse(sheetSource);
        return handler.getRows();
    }

    /**
     * 初始化这个处理程序 将
     *
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public List<String[]> process() throws IOException, OpenXML4JException,
            ParserConfigurationException, SAXException {

        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(
                this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        List<String[]> list = null;
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader
                .getSheetsData();
        int index = 0;
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            String sheetNameTemp = iter.getSheetName();
            if (this.sheetName.equals(sheetNameTemp)) {
                list = processSheet(styles, strings, stream);
                stream.close();
                ++index;
            }
        }
        return list;
    }

    /**
     * 读取Excel
     *
     * @param path       文件路径
     * @param sheetName  sheet名称
     * @param minColumns 列总数
     * @return
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws OpenXML4JException
     * @throws IOException
     */
    private static List<String[]> readerExcel(String path, String sheetName,
                                              int minColumns) throws IOException, OpenXML4JException,
            ParserConfigurationException, SAXException {
        OPCPackage p = OPCPackage.open(path, PackageAccess.READ);
        ExcelBigUtil xlsx2csv = new ExcelBigUtil(p, System.out,
                sheetName, minColumns);
        List<String[]> list = xlsx2csv.process();
        p.close();
        return list;
    }

    /**
     * 读取Excel
     *
     * @param inputStream
     * @param sheetName
     * @param minColumns
     * @return
     * @throws IOException
     * @throws OpenXML4JException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    private static List<String[]> readerExcel(InputStream inputStream, String sheetName,
                                              int minColumns) throws IOException, OpenXML4JException,
            ParserConfigurationException, SAXException {
        OPCPackage p = OPCPackage.open(inputStream);
        ExcelBigUtil xlsx2csv = new ExcelBigUtil(p, System.out,
                sheetName, minColumns);
        List<String[]> list = xlsx2csv.process();
        p.close();
        return list;
    }

    /**
     * 读取数据
     *
     * @param path
     * @param sheetName
     * @param colNum
     * @param c
     * @return
     */
    public static <T> List<T> readData(String path, String sheetName, int colNum, Class<T> c) {
        List<T> list = Lists.newArrayList();
        try {
            List<String[]> stringList = ExcelBigUtil.readerExcel(path, sheetName, colNum);
            return dealData(stringList, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 读取数据
     *
     * @param file
     * @param sheetName
     * @param colNum
     * @param c
     * @return
     */
    public static <T> List<T> readData(MultipartFile file, String sheetName, int colNum, Class<T> c) {
        List<T> list = Lists.newArrayList();
        try {
            List<String[]> stringList = ExcelBigUtil.readerExcel(file.getInputStream(), sheetName, colNum);
            return dealData(stringList, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 数据处理
     *
     * @param list
     * @param c
     * @param <T>
     * @return
     */
    public static <T> List<T> dealData(List<String[]> list, Class<T> c) {
        List<T> res = Lists.newArrayList();
        try {
            // 标题
            String[] titleRow = list.get(0);
            Map<Integer, String> colMap = new LinkedMap();
            for (int i = 0; i < titleRow.length; i++) {
                if (StringUtil.isBlank(titleRow[i])) {
                    break;
                }
                colMap.put(i, titleRow[i]);
            }
            // System.out.println(colMap);

            // 字段映射
            Field[] fields = c.getDeclaredFields();
            // 属性和表格列对应
            Map<String, Map<String, String>> classMap = new LinkedHashMap();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Ex.class)) {
                    Ex annotation = field.getAnnotation(Ex.class);
                    Map<String, String> propertyMap = new LinkedHashMap<>();
                    propertyMap.put("field", field.getName());
                    propertyMap.put("convertType", field.getGenericType().toString());
                    classMap.put(annotation.name(), propertyMap);
                }
                field.getClass();
            }
            // System.out.println(classMap);

            // 有效数据读取
            int rowNum = 1;
            for (String[] row : list) {
                if (rowNum++ == 1) {
                    continue;
                }
                T obj = c.newInstance();
                for (int i = 0; i < row.length; i++) {
                    if (classMap.get(colMap.get(i)) == null) {
                        continue;
                    }
                    String property = classMap.get(colMap.get(i)).get("field");
                    String convertType = classMap.get(colMap.get(i)).get("convertType");
                    // System.out.println(property + ":" + row[i]);
                    if (StringUtil.isNotBlank(property)) {
                        Object value = convertValue(row[i], convertType);
                        if (value != null) {
                            ReflectUtil.setProperty(obj, property, value);
                        }
                    }
                }
                res.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据反射转成不同的类型
     *
     * @param object
     * @param convertType
     * @return
     */
    public static Object convertValue(String object, String convertType) {
        Object res = null;
        if (object == null) {
            return null;
        }
        try {
            switch (convertType) {
                case "class java.lang.Integer":
                case "int":
                    res = Integer.parseInt(object);
                    break;
                case "class java.lang.Long":
                case "long":
                    res = Long.parseLong(object);
                    break;
                case "class java.lang.Float":
                case "float":
                    res = Float.parseFloat(object);
                    break;
                case "class java.lang.Double":
                case "double":
                    res = Double.parseDouble(object);
                    break;
                case "class java.lang.Boolean":
                case "boolean":
                    res = Boolean.parseBoolean(object);
                    break;
                case "class java.lang.String":
                    res = object;
                    break;
                default:
                    res = object;
                    break;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        return res;
    }
}
