package com.ztg;

import com.google.common.collect.Lists;
import com.ztg.util.StringUtil;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 打印类的属性名和注释
 * @author: zhoutg
 * @time: 2021/5/18 14:48
 */
public class PrintUtil {

    /**
     * 输入字段名和注释
     *
     * @param path
     */
    public static void print(String path) {
        List<String> list = Lists.newArrayList();
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(path), "UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                list.add(line);
                sb.append(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String text = sb.toString();
        // 截取有效内容
        if (sb.indexOf("serialVersionUID = 1L;") >= 0) {
            text = sb.substring(sb.indexOf("serialVersionUID = 1L;") + 22);
        } else {
            text = sb.substring(sb.indexOf("{") + 1);
        }
        List<String> stringList = Lists.newArrayList(text.split(";"));
        Map<String, String> map = new LinkedHashMap<>();
        String key = "";
        String value = "";
        for (String s : stringList) {
            try {
                if (s.indexOf("/*** ") >= 0) {
                    value = s.substring(s.indexOf("/***") + 4, s.indexOf("*/")).trim();
                } else if (s.indexOf("//") >= 0) {
                    value = s.substring(s.indexOf("//") + 2, s.indexOf("private")).trim();
                }
                int index = s.indexOf("=");
                if (index >= 0) { // 包含“=”，例如：// 科室列表private List<Long> deptList = Lists.newLinkedList()
                    StringBuffer keySb = new StringBuffer();
                    boolean insertflag = false; // 是否有效字符插入
                    for (int i = index - 1; i >= 0; i--) {
                        char ch = s.charAt(i);
                        if (insertflag && StringUtil.isBlank(String.valueOf(ch))) {
                            break;
                        }
                        if (StringUtil.isNotBlank(String.valueOf(ch))) {
                            keySb.append(ch);
                            insertflag = true;
                        }
                    }
                    keySb.reverse();// 反转
                    key = keySb.toString();
                } else {    // 不包含“=”，例如： /*** 英文名称*/private String enName;
                    key = s.substring(s.lastIndexOf(" ")).trim();
                }
                map.put(key, value);
            } catch (Exception e) {
                // 捕获异常不处理
            }
        }
        for (String property : map.keySet()) {
            System.out.println(property + "：" + map.get(property) + "");
        }
    }

    public static void main(String[] args) {
        String path = "E:\\projectTest\\tally\\src\\main\\java\\com\\ztg\\vo\\RecordPlayerSaveVO.java";
        print(path);
    }
}
