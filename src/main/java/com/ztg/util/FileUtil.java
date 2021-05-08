package com.ztg.util;

import com.google.common.collect.Lists;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * @description: 文件工具类
 * @author: zhoutg
 * @time: 2021/5/8 15:19
 */
public class FileUtil {

    // 项目根目录
    public static final String ROOT_PATH = System.getProperty("user.dir");

    /**
     * 按行读取
     *
     * @param path
     * @return
     */
    private static List<String> readByLine(String path) {
        List<String> list = Lists.newArrayList();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(path), "UTF-8"));
            String line = "";
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 按行读取(文件和程序同级目录)
     *
     * @param path
     * @return
     */
    private static List<String> readByLineRoot(String path) {
        List<String> list = Lists.newArrayList();
        try {
            File file = new File(ROOT_PATH);
            String filePath = file.getParent() + File.separator + path;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(filePath), "UTF-8"));
            String line = "";
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 以流的方式按行读取
     *
     * @param path
     * @return
     */
    private static List<String> readLineStream(String path) {
        List<String> list = Lists.newArrayList();
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("a1.txt");
            InputStream inputStream = resource.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(inputStream));
            String line = "";
            while((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 按行写入文件
     *
     * @param list
     * @param fileName
     */
    public static void writeLine(List<String> list, String fileName) {
        for (String s : list) {
            try {
                BufferedWriter bw =
                    new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(fileName), "UTF-8"));
                bw.write(s);
                bw.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> list = Lists.newArrayList("咳嗽", "胸痛");
        writeLine(list, "bb.txt");
    }
}
