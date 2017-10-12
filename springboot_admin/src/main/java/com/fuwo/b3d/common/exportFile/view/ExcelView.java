package com.fuwo.b3d.common.exportFile.view;

import com.fuwo.b3d.order.model.Order;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by jin.zhang@fuwo.com on 2017-09-15.
 */

public class ExcelView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        String fileName = DateFormatUtils.format(new Date(), "yyyyMMdd") + ".xls";

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");

        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);


        String type = String.valueOf(model.get("type"));
        if ("order".equals(type)) {
            //订单导出
            List<Order> orders = (List<Order>) model.get("orders");
            workbook.setSheetName(0, "订单流水");

            // create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("订单编号");
            header.getCell(0).setCellStyle(style);
            header.createCell(1).setCellValue("创建时间");
            header.getCell(1).setCellStyle(style);
            header.createCell(2).setCellValue("姓名");
            header.getCell(2).setCellStyle(style);
            header.createCell(3).setCellValue("电话/邮箱");
            header.getCell(3).setCellStyle(style);
            header.createCell(4).setCellValue("用户等级");
            header.getCell(4).setCellStyle(style);
            header.createCell(5).setCellValue("支付方式");
            header.getCell(5).setCellStyle(style);
            header.createCell(6).setCellValue("订单状态");
            header.getCell(6).setCellStyle(style);
            header.createCell(7).setCellValue("金额");
            header.getCell(7).setCellStyle(style);
            header.createCell(8).setCellValue("购买内容");
            header.getCell(8).setCellStyle(style);
            header.createCell(9).setCellValue("取消原因");
            header.getCell(9).setCellStyle(style);

            int rowCount = 1;
            for (Order item : orders) {
                Row userRow = sheet.createRow(rowCount++);
                userRow.createCell(0).setCellValue(item.getNo());
                userRow.createCell(1).setCellValue(item.getCreated());
                userRow.createCell(2).setCellValue(item.getUserInfo() == null ? "" : item.getUserInfo().getUsername());
                userRow.createCell(3).setCellValue(item.getUserInfo() == null ? "" :
                        (item.getUserInfo() == null ? "" : item.getUserInfo().getProfile().getMobile()) + "/" + item.getUserInfo().getEmail());
                userRow.createCell(4).setCellValue("ret");
                userRow.createCell(5).setCellValue("福币");
                userRow.createCell(6).setCellValue(item.getState() == null ? "" : item.getState().getDesc());
                userRow.createCell(7).setCellValue(item.getAmount());
                userRow.createCell(8).setCellValue(item.getDealType() == null ? "" : item.getDealType().getDesc());
                userRow.createCell(9).setCellValue(item.getCancelledReason());

            }
        }


    }

}
