package com.newview.bysj.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XlsHelper {
    private final static int[] beginningBytes2007 = {80, 75, 3, 4, 20, 0, 6,
            0, 8, 0, 0, 0, 33, 0};
    private final static int[] beginningBytes2003 = {208, 207, 17, 224, 161,
            177, 26, 225, 0, 0, 0, 0, 0, 0};

    // 80,75,3,4,20,0,6,0,8,0,0,0,33,0,
    // 208,207,17,224,161,177,26,225,0,0,0,0,0,0

    public static HSSFSheet getSheet(InputStream is, String sheetname)
            throws IOException {

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        return hssfWorkbook.getSheet(sheetname);
    }

    public static HSSFSheet getSheet(InputStream is) throws IOException {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        return hssfWorkbook.getSheetAt(0);
    }

    //根据文件前14个字节的内容，判断该文件是否是2007版本的excel的文件
    public static boolean is2007(File file) throws Exception {
        return is2007(new FileInputStream(file));
    }

    //根据文件前14个字节的内容，判断该流是否是2007版本的excel的文件
    public static boolean is2007(InputStream ins) throws Exception {
        boolean is2007 = true;
        for (int i = 0; i < beginningBytes2007.length; i++) {
            if (ins.read() != beginningBytes2007[i]) {
                is2007 = false;
            }
        }
        ins.close();
        return is2007;
    }

}



