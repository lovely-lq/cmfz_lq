package com.baizhi.lq.entity;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UserContenver extends StringImageConverter {
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        System.out.println(value);
        String[] split = value.split("/");
        value = split[split.length - 1];
        System.out.println(value);
        String uri = property + "\\src\\main\\webapp\\upload\\photo\\" + value;
        System.out.println(uri);
        return new CellData(FileUtils.readFileToByteArray(new File(uri)));

    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return cellData.getStringValue();
    }
}
