package com.newview.bysj.xls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SheetDbInterface {
    //获得表头
    String[] getHeader() throws FileNotFoundException, IOException;

    //获得当前行
    List<Map<String, String>> getRow() throws FileNotFoundException, IOException;

    //获得制定行
    List<Map<String, String>> getRow(Integer row) throws FileNotFoundException, IOException;

    //获得总行数
    Integer getRows() throws FileNotFoundException, IOException;

    //获得总列数
    Integer getColumn() throws FileNotFoundException, IOException;

    //如果单元格为数值则转换成double
    public Object getCellWithType(int row, String header) throws FileNotFoundException, IOException;
}
