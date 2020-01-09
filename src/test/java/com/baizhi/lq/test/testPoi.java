package com.baizhi.lq.test;


import com.baizhi.lq.dao.BannerDao;
import com.baizhi.lq.entity.Banner;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testPoi {
    @Autowired
    BannerDao bannerDao;

    @Test
    public void test1() {
        List<Banner> banners = bannerDao.selectAll();
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建一个工作薄
        HSSFSheet sheet = workbook.createSheet();
        //创建一个行对象
        HSSFRow row = sheet.createRow(0);
        String[] str = {"ID", "标题", "封面", "超链接", "创建时间", "描述", "状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        // 通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 通过workbook对象获取数据格式化处理对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        // 指定格式化样式 如 yyyy-MM-dd
        short format = dataFormat.getFormat("yyyy-MM-dd");
        // 为样式对象 设置格式化处理
        cellStyle.setDataFormat(format);

        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());

            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreateDate());
            row1.createCell(5).setCellValue(banner.getDescription());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("E:\\JAVA\\后期项目\\day6-poi\\" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("E:\\JAVA\\后期项目\\day6-poi\\1577947667746.xls")));
        HSSFSheet sheet = workbook.getSheet("Sheet0");
        ArrayList<Banner> banners = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Banner banner = new Banner();
            HSSFRow row = sheet.getRow(i);
            String id = row.getCell(0).getStringCellValue();
            String title = row.getCell(1).getStringCellValue();
            banner.setId(id);
            banner.setTitle(title);
            banners.add(banner);

        }
        System.out.println(banners);

    }
}
