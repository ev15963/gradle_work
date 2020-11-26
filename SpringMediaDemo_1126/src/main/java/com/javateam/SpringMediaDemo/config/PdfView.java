/**
 * 
 */
package com.javateam.SpringMediaDemo.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.javateam.SpringMediaDemo.domain.ProductVO;

import lombok.extern.java.Log;

/**
 * PDF View
 * @author javateam
 *
 */
@Component("pdfView")
@Log
public class PdfView extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, 
									Document document, 
									PdfWriter writer,
									HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		
		// 한글 처리
		String fontName = "/font/NanumBarunGothic.ttf"; 
		BaseFont bfont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font font = new Font(bfont, 12);

		ProductVO product = (ProductVO)model.get("product");
		
		log.info("product : " + product);
		
		PdfPTable table = new PdfPTable(3); // column number(컬럼수)
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(BaseColor.YELLOW);
        table.setWidthPercentage(100.0f);
        // table.setWidths(new float[] {3.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
		
        table.addCell("Id");
        table.addCell("Name");
        table.addCell("Detail");
        
        table.addCell(new Paragraph(product.getId(), font));
        table.addCell(new Paragraph(product.getName(), font));
        table.addCell(new Paragraph(product.getDetail(), font));
        
        document.add(table);
	}

} //