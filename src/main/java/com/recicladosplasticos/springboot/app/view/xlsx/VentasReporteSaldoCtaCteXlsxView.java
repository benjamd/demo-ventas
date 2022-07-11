package com.recicladosplasticos.springboot.app.view.xlsx;
 

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle; 
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
 


@Component("/ventas/reporte/saldopendientectacte.xlsx")
public class VentasReporteSaldoCtaCteXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
		
		 response.setHeader("Content-Disposition", "attachment; filename=\"reporte_ventas_saldos_ctacte.xlsx\" ");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		 
		
		@SuppressWarnings("unchecked")
		Map<String, Double> documentos =   (Map<String, Double>) model.get("documentos");
		String tipodoc = (String) model.get("tipodoc");
		Sheet sheet = workbook.createSheet("Saldo Cta Cte");
 
			
		//HEADER
		CellStyle theaderStyle = workbook.createCellStyle();
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		theaderStyle.setFont(boldFont);
		
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		theaderStyle.setAlignment(HorizontalAlignment.CENTER); 
		
 
		//FORMATO CUERPO CLIENTE - NRO. FACTURA
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyStyle.setBorderLeft(BorderStyle.MEDIUM);
			
		//FORMATO CUERPO NETO - IVA - TOTAL
		DataFormat format = workbook.createDataFormat();
		CellStyle tbodymoneyStyle = workbook.createCellStyle();
		tbodymoneyStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodymoneyStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodymoneyStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodymoneyStyle.setBorderLeft(BorderStyle.MEDIUM);
		tbodymoneyStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		
		CellStyle tituloStyle = workbook.createCellStyle();
		tituloStyle.setFont(boldFont);
		
		Row empresa = sheet.createRow(0);
		empresa.createCell(0).setCellValue("EMPRESA");
		empresa.setRowStyle(tituloStyle);
		Row cuit = sheet.createRow(1);
		cuit.createCell(0).setCellValue("CUIT: XX-XXXXXXXX-X");
		cuit.setRowStyle(tituloStyle);
		Row reporte = sheet.createRow(3);
	 
		 
		reporte.createCell(0).setCellValue("Reporte Ventas - Saldos Cuenta Corriente: ".concat(tipodoc));
		reporte.getCell(0).setCellStyle(tituloStyle);
 
  
		Row header = sheet.createRow(5);
		header.createCell(0).setCellValue("CLIENTE");
		header.createCell(1).setCellValue("SALDO");
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);


		int rownumber = 6;
		Row fila = sheet.createRow(rownumber);
		Cell cell = null;
		
		Double saldo = 0.0;
		
        Map<String, Double> documentosOrdenados = new TreeMap<String, Double>(documentos);


		for (Map.Entry<String, Double> entry : documentosOrdenados.entrySet()) {
	     	
		  fila.createCell(0).setCellValue(entry.getKey());
		  fila.getCell(0).setCellStyle(tbodyStyle);
		  fila.createCell(1).setCellValue(entry.getValue());
		  fila.getCell(1).setCellStyle(tbodymoneyStyle);

		  saldo = saldo + entry.getValue();
		  rownumber = rownumber+ 1;
		  fila = sheet.createRow(rownumber);
		 	  
	   }
		

		
		//FOOTER LEFT
		CellStyle tfooterLeftStyle = workbook.createCellStyle();
		tfooterLeftStyle.setFont(boldFont);
		tfooterLeftStyle.setBorderLeft(BorderStyle.MEDIUM);
		tfooterLeftStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterLeftStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterLeftStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterLeftStyle.setAlignment(HorizontalAlignment.CENTER); 
		  
		Row filatotal = sheet.createRow(rownumber);
		cell = filatotal.createCell(0);
		cell.setCellStyle(tfooterLeftStyle);
		
		cell = filatotal.createCell(1);
		cell.setCellValue(saldo);
		theaderStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		cell.setCellStyle(theaderStyle);
		
		 
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
	}
	

}
