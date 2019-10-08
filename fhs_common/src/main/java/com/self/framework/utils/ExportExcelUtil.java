package com.self.framework.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author qiuhang
 * @desc 导出excel报表工具类
 * 更多esaypoi 导出功能详情 easypoi.mydoc.io
 * @date 2019/9/24/024
 */
public class ExportExcelUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

    public static <T> void exportExcel(HttpServletResponse response, String fileName,
                                        List<T> listData, Class<T> classType ) throws IOException {
        exportExcel(response,fileName, fileName + "数据报表", fileName, listData, classType);
    }

    public static <T> void exportExcel(HttpServletResponse response, String fileName,
                                        String titleName, String sheetName,
                                        List<T> listData, Class<T> classType ) throws IOException {
        ExportParams exportParams = new ExportParams(fileName, sheetName, ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, classType, listData);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> Workbook exportBigExcel(Workbook workBook, ExportParams params,
                                          List<T> listData, Class<T> classType){
        params.setType(ExcelType.XSSF);
        workBook = ExcelExportUtil.exportBigExcel(params, classType, listData);
        return workBook;
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook)
            throws IOException{
        response.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        workbook.write(response.getOutputStream());
    }
}
