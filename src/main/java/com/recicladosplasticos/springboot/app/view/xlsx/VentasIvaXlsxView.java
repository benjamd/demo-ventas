package com.recicladosplasticos.springboot.app.view.xlsx;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.recicladosplasticos.springboot.app.models.entity.Documento;

@Component("/ventas/reporte/exportarivaventas.xlsx")
public class VentasIvaXlsxView extends AbstractXlsxView  {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
		
		
		 response.setHeader("Content-Disposition", "attachment; filename=\"libro_iva_ventas.xlsx\" ");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		 
 
		
 
		@SuppressWarnings("unchecked")
		List<Documento> documentos =   (List<Documento>) model.get("documentos");
		
	 
		
		 Date fechainicio =   (Date) model.get("fechainicio");
		 Date fechafin =  (Date)  model.get("fechafin");
			
		Sheet sheet = workbook.createSheet("IVA ventas");
		DecimalFormat df = new DecimalFormat("00000000");
		 
		//FUENTES COLOR Y SIZE 
		DataFormat format = workbook.createDataFormat();
		//blackElevenFont.setFontHeightInPoints((short) 11 );
		Font redFont = workbook.createFont();
		redFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		redFont.setFontHeightInPoints((short) 10 );
		redFont.setFontName("Liberation Sans Narrow");
		Font boldFont = workbook.createFont();
		boldFont.setBold(true);
		Font blackFooterFont = workbook.createFont();
		//blackFooterFont.setFontHeightInPoints((short) 11 );
		blackFooterFont.setBold(true);		
		Font redFooterFont = workbook.createFont();
		redFooterFont.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		//redFooterFont.setFontHeightInPoints((short) 11 );
		redFooterFont.setBold(true);	
		
		CellStyle redMoneyStyle = workbook.createCellStyle();
		redMoneyStyle.setBorderBottom(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderTop(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderRight(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderLeft(BorderStyle.MEDIUM);
		redMoneyStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		redMoneyStyle.setFont(redFont);
		
		
		//HEADER
		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setFont(boldFont);
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		theaderStyle.setAlignment(HorizontalAlignment.CENTER); 
		
		//FOOTER NEGRO		
		CellStyle tfooterStyle = workbook.createCellStyle();
		tfooterStyle.setFont(blackFooterFont);
		tfooterStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		
		tfooterStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterStyle.setBorderRight(BorderStyle.MEDIUM);
		tfooterStyle.setBorderLeft(BorderStyle.MEDIUM);
		tfooterStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterStyle.setAlignment(HorizontalAlignment.RIGHT); 
		
		//FOOTER ROJO
		CellStyle tfooterRedStyle = workbook.createCellStyle();
		tfooterRedStyle.setFont(redFooterFont);
		tfooterRedStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		
		tfooterRedStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterRedStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterRedStyle.setBorderRight(BorderStyle.MEDIUM);
		tfooterRedStyle.setBorderLeft(BorderStyle.MEDIUM);
		tfooterRedStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterRedStyle.setAlignment(HorizontalAlignment.RIGHT); 
	
		//CELDAS ROJOS PARA NUMEROS NEGATIVOS
		
		CellStyle redStyle = workbook.createCellStyle();
		redStyle.setBorderBottom(BorderStyle.MEDIUM);
		redStyle.setBorderTop(BorderStyle.MEDIUM);
		redStyle.setBorderRight(BorderStyle.MEDIUM);
		redStyle.setBorderLeft(BorderStyle.MEDIUM);
		redStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		redStyle.setFont(redFont);
		
		
		/*
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
		*/
		
		//FORMATO CUERPO FECHA
		CellStyle tbodydateStyle = workbook.createCellStyle();
		tbodydateStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderLeft(BorderStyle.MEDIUM);
 		CreationHelper createHelper = workbook.getCreationHelper();
		tbodydateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
			
		//FORMATO CUERPO CLIENTE - NRO. DOC
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyStyle.setBorderLeft(BorderStyle.MEDIUM);
			
		//FORMATO CUERPO NETO - IVA - TOTAL
		
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
		tbodydateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		

		
		reporte.createCell(0).setCellValue("Reporte de ventas - IVA Ventas");
		reporte.createCell(3).setCellValue("Desde:");
		reporte.createCell(5).setCellValue("Hasta:");
		reporte.getCell(0).setCellStyle(tituloStyle);
		reporte.getCell(3).setCellStyle(tituloStyle);
		reporte.getCell(5).setCellStyle(tituloStyle);
 
		
		CellStyle fechaStyle = workbook.createCellStyle();
		fechaStyle.setFont(boldFont);
		fechaStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		
	 
		if(fechainicio != null) {
			reporte.createCell(4);
			reporte.getCell(4).setCellValue(fechainicio);
			reporte.getCell(4).setCellStyle(fechaStyle);
		} 
		
		if(fechafin != null) {
			reporte.createCell(6);
			reporte.getCell(6).setCellValue(fechafin);
			reporte.getCell(6).setCellStyle(fechaStyle);
		} 
		
		
		
		Row header = sheet.createRow(5);
		header.createCell(0).setCellValue("FECHA");
		header.createCell(1).setCellValue("CLIENTE");
		header.createCell(2).setCellValue("CUIT");
		header.createCell(3).setCellValue("DOCUMENTO");
		header.createCell(4).setCellValue("NUMERO");
		header.createCell(5).setCellValue("NETO");
		header.createCell(6).setCellValue("IVA");
		header.createCell(7).setCellValue("TOTAL");
		
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		header.getCell(5).setCellStyle(theaderStyle);
		header.getCell(6).setCellStyle(theaderStyle);
		header.getCell(7).setCellStyle(theaderStyle);
				
		int rownumber = 6;
		Row fila = sheet.createRow(rownumber);
		 Cell cell = null;
		
		for(int i = 0; i <   documentos.size() ; i++) {
		
			//FECHA
			cell = fila.createCell(0);
			cell.setCellStyle(tbodydateStyle);
			//CLIENTE
			cell = fila.createCell(1);
			cell.setCellStyle(tbodyStyle);
			//CUIT
			cell = fila.createCell(2);
			cell.setCellStyle(tbodyStyle);
			//DOCUMENTO
			cell = fila.createCell(3);
			cell.setCellStyle(tbodyStyle);
			//NRO. DOC
			cell = fila.createCell(4);
			cell.setCellStyle(tbodyStyle);
			//NETO
			cell = fila.createCell(5);
			cell.setCellStyle(tbodymoneyStyle);
			//IVA
			cell = fila.createCell(6);
			cell.setCellStyle(tbodymoneyStyle);
			//TOTAL
			cell = fila.createCell(7);
			cell.setCellStyle(tbodymoneyStyle);
			
			rownumber = rownumber+ 1;
			fila = sheet.createRow(rownumber);
			
		}
		
		
		BigDecimal totalNeto = BigDecimal.valueOf(0);
		BigDecimal totalIva = BigDecimal.valueOf(0);
		BigDecimal totalVentas = BigDecimal.valueOf(0);
		
		documentos.sort(Comparator.comparing(Documento::getFecha));
		
		rownumber = 6;
		for(Documento doc: documentos) {
		  
		  fila = sheet.getRow(rownumber ++);
		  
		  cell = fila.getCell(0); 
		  cell.setCellValue(doc.getFecha());
		  
		  cell = fila.getCell(1);  
		  cell.setCellValue(doc.getCliente().getRazonsocial());
		  
		  cell = fila.getCell(2);  
		  cell.setCellValue(doc.getCliente().getCuit());
		  
		  cell = fila.getCell(3); 
		  cell.setCellValue(doc.getDocumento());
		  		  
		  cell = fila.getCell(4); 
		  cell.setCellValue(doc.getPrefijo()+"-"+ df.format(doc.getNumero()));
		 
		  if(!doc.getDocumento().equals("Nota de Crédito")) {
			  
			  cell = fila.getCell(5); 
			  cell.setCellValue(doc.getImporte().doubleValue());
			  
			  cell = fila.getCell(6); 
			  cell.setCellValue(doc.getImporteiva().doubleValue());
	 
			  cell = fila.getCell(7); 
			  cell.setCellValue(doc.getImportetotal().doubleValue());
			  
			  totalNeto = totalNeto .add( doc.getImporte());
			  totalIva = totalIva .add( doc.getImporteiva());
			  totalVentas = totalVentas.add(doc.getImportetotal());

			  
		  } else {
			  
			  cell = fila.getCell(5); 
			  cell.setCellValue(-doc.getImporte().doubleValue());
			  cell.setCellStyle(redMoneyStyle);
			  
			  cell = fila.getCell(6); 
			  cell.setCellValue(-doc.getImporteiva().doubleValue());
			  cell.setCellStyle(redMoneyStyle);
			  
			  cell = fila.getCell(7); 
			  cell.setCellValue(-doc.getImportetotal().doubleValue());
			  cell.setCellStyle(redMoneyStyle);
			  
			  totalNeto = totalNeto.subtract( doc.getImporte()).setScale(2, RoundingMode.HALF_EVEN);   
			  totalIva = totalIva.subtract(doc.getImporteiva()).setScale(2, RoundingMode.HALF_EVEN);
			  totalVentas = totalVentas.subtract(doc.getImportetotal()).setScale(2, RoundingMode.HALF_EVEN);
		  }
	   }
		
		
		
		//FOOTER CENTER
		CellStyle tfooterCenterStyle = workbook.createCellStyle();
		tfooterCenterStyle.setFont(boldFont);
		tfooterCenterStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterCenterStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterCenterStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterCenterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterCenterStyle.setAlignment(HorizontalAlignment.CENTER); 
		
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
		cell.setCellStyle(tfooterCenterStyle);
		cell = filatotal.createCell(2);
		cell.setCellStyle(tfooterCenterStyle);
		
		cell = filatotal.createCell(3);
		cell.setCellStyle(tfooterCenterStyle);

		cell = filatotal.createCell(4);
		cell.setCellStyle(tfooterCenterStyle);
		
		cell = filatotal.createCell(5);
		cell.setCellValue(totalNeto.doubleValue());
		theaderStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		if(totalNeto.doubleValue() >= .0) {
			 
			cell.setCellStyle(tfooterStyle);
		} else {
			cell.setCellStyle(tfooterRedStyle);
		}
		
		//cell.setCellStyle(theaderStyle);
		
		cell = filatotal.createCell(6);
		cell.setCellValue(totalIva.doubleValue());
		if(totalIva.doubleValue() >= .0) {
			 
			cell.setCellStyle(tfooterStyle);
		} else {
			cell.setCellStyle(tfooterRedStyle);
		}
		//cell.setCellStyle(theaderStyle);
		
		cell = filatotal.createCell(7);
		cell.setCellValue(totalVentas.doubleValue());
		if(totalVentas.doubleValue() >= .0) {
			 
			cell.setCellStyle(tfooterStyle);
		} else {
			cell.setCellStyle(tfooterRedStyle);
		}
		
		//cell.setCellStyle(theaderStyle);
 
		 
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.LEGAL_PAPERSIZE);
		sheet.getPrintSetup().setLandscape(true);
		
	}

}
