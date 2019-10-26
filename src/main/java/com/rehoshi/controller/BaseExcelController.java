package com.rehoshi.controller;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.rehoshi.dto.excel.ExcelMergeInfo;
import com.rehoshi.dto.excel.ExcelRow;
import com.rehoshi.dto.excel.adapt.TableInfo;
import com.rehoshi.util.CollectionUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseExcelController extends BaseController {

    protected HttpServletResponse response;

    @ModelAttribute
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public <T extends ExcelRow<?>> void export(Class<T> cls, List<T> rowData, String sheetName) {
        export(cls, rowData, sheetName, null);
    }

    public <T extends ExcelRow<?>> void export(Class<T> cls, List<T> rowData, String sheetName, List<ExcelMergeInfo> mergeInfoList) {
        try {
            ExcelWriter writer = prepareWriter(sheetName);

            Sheet sheet = new Sheet(1, 1);
            sheet.setSheetName(sheetName);
            sheet.setClazz(cls);
            sheet.setAutoWidth(true);
            writer.write(rowData, sheet);

            CollectionUtil.foreach(mergeInfoList, data -> {
                writer.merge(data.getStartRow(), data.getEndRow(), data.getStartCol(), data.getEndCol());
            });
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void export(TableInfo tableInfo, String sheetName) {
        try {
            ExcelWriter excelWriter = prepareWriter(sheetName);
            Sheet sheet = new Sheet(1, 1) ;
            sheet.setSheetName(sheetName);
            com.alibaba.excel.metadata.Table table = new com.alibaba.excel.metadata.Table(1) ;
            List<List<String>> headList = new ArrayList<>() ;
            CollectionUtil.foreach(tableInfo.getColumns(), data -> {
                headList.add(Arrays.asList(data)) ;
            });
            table.setHead(headList);
            excelWriter.write1(tableInfo.getValues(), sheet, table) ;
            excelWriter.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ExcelWriter prepareWriter(String sheetName) throws IOException {
        response.addHeader("pragma", "NO-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addDateHeader("Expries", 0);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String filename = new String((sheetName + ".xlsx").getBytes("UTF-8"), "ISO8859_1");
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        return new ExcelWriter(null, response.getOutputStream(), ExcelTypeEnum.XLSX, true, new WriteHandler() {
            @Override
            public void sheet(int i, org.apache.poi.ss.usermodel.Sheet sheet) {
                sheet.setAutoFilter(new CellRangeAddress(0, 100, 0, 100)) ;
            }

            @Override
            public void row(int i, Row row) {
            }

            @Override
            public void cell(int i, Cell cell) {
                CellStyle cellStyle = cell.getCellStyle();
                if (!cellStyle.getFillPatternEnum().equals(FillPatternType.SOLID_FOREGROUND)) {
                    cell.setCellStyle(createStyle(cell.getSheet().getWorkbook()));
                }
            }

            private CellStyle createStyle(Workbook workbook) {
                CellStyle cellStyle = workbook.createCellStyle();
                // 下边框
                cellStyle.setBorderBottom(BorderStyle.THIN);
                // 左边框
                cellStyle.setBorderLeft(BorderStyle.THIN);
                // 上边框
                cellStyle.setBorderTop(BorderStyle.THIN);
                // 右边框
                cellStyle.setBorderRight(BorderStyle.THIN);
                // 水平对齐方式
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                // 垂直对齐方式
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                return cellStyle;
            }
        });
    }


}
