package com.newview.bysj.initialize.excel;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import org.unitils.core.UnitilsException;
import org.unitils.dbunit.datasetfactory.DataSetFactory;
import org.unitils.dbunit.util.MultiSchemaDataSet;

public class MultiSchemaXlsDataSetFactory implements DataSetFactory {

    protected String defaultSchemaName;

    //初始�?
    public void init(Properties configuration, String defaultSchemaName) {
        this.defaultSchemaName = defaultSchemaName;
    }

    //创建数据�?
    public MultiSchemaDataSet createDataSet(File... dataSetFiles) {
        try {
            MultiSchemaXlsDataSetReader xlsDataSetReader = new MultiSchemaXlsDataSetReader(
                    defaultSchemaName);
            return xlsDataSetReader.readDataSetXls(dataSetFiles);
        } catch (Exception e) {
            throw new UnitilsException("创建数据集失�?: "
                    + Arrays.toString(dataSetFiles), e);
        }
    }

    // 获取数据集文件的扩展�?
    public String getDataSetFileExtension() {
        return "xls";
    }

}
