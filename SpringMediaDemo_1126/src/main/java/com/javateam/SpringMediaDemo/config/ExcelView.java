/**
 * 
 */
package com.javateam.SpringMediaDemo.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.javateam.SpringMediaDemo.domain.ProductVO;

/**
 * Excel View
 * @author javateam
 *
 */
@Component("excelView")
public class ExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, 
									  Workbook workbook, 
									  HttpServletRequest request,
									  HttpServletResponse response) throws Exception {

		ProductVO product = (ProductVO)model.get("product");
		
		Sheet sheet = workbook.createSheet("상품 시트");
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        Row row = null;
        Cell cell = null;
        int rowCount = 0;
        int colCount = 0;
 
        // create header cells
        row = sheet.createRow(rowCount++);
 
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Id");
        
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Name");
        
        cell = row.createCell(colCount++);
        cell.setCellStyle(style);
        cell.setCellValue("Detail");
        
        // create data cells
        row = sheet.createRow(rowCount++);
        colCount = 0;
        row.createCell(colCount++).setCellValue(product.getId());
        row.createCell(colCount++).setCellValue(product.getName());
        row.createCell(colCount++).setCellValue(product.getDetail());
        
        for (int i = 0; i < 3; i++)
            sheet.autoSizeColumn(i, true);
		
	} //

}