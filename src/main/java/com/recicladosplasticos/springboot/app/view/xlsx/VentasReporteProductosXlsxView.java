package com.recicladosplasticos.springboot.app.view.xlsx;

import java.math.BigDecimal;
import java.math.RoundingMode;
 
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
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.recicladosplasticos.springboot.app.models.entity.Documento;
import com.recicladosplasticos.springboot.app.models.entity.Item;
 
@Component("/ventas/reporte/exportarreporteproductos.xlsx")
public class VentasReporteProductosXlsxView extends AbstractXlsxView  {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
		 response.setHeader("Content-Disposition", "attachment; filename=\"reporte_ventas_productos.xlsx\" ");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		 
 
		
 
		@SuppressWarnings("unchecked")
		List<Documento> documentos =   (List<Documento>) model.get("documentos");
		String titulo = (String) model.get("titulo");
	 
		
		 Date fechainicio =   (Date) model.get("fechainicio");
		 Date fechafin =  (Date)  model.get("fechafin");
			
		Sheet sheet = workbook.createSheet("Ventas Productos");
		 
		
		//FUENTES COLOR Y SIZE 
		DataFormat format = workbook.createDataFormat();
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
		
		
		//FOOTER NEGRO	PESO	
		CellStyle tfooterPesoStyle = workbook.createCellStyle();
		tfooterPesoStyle.setFont(blackFooterFont);
		tfooterPesoStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00"));
		
		tfooterPesoStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterPesoStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterPesoStyle.setBorderRight(BorderStyle.MEDIUM);
		tfooterPesoStyle.setBorderLeft(BorderStyle.MEDIUM);
		tfooterPesoStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterPesoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterPesoStyle.setAlignment(HorizontalAlignment.RIGHT); 
		
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
		
		//FOOTER ROJO PESO
		CellStyle tfooterPesoRedStyle = workbook.createCellStyle();
		tfooterPesoRedStyle.setFont(redFooterFont);
		tfooterPesoRedStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00"));
		
		tfooterPesoRedStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterPesoRedStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterPesoRedStyle.setBorderRight(BorderStyle.MEDIUM);
		tfooterPesoRedStyle.setBorderLeft(BorderStyle.MEDIUM);
		tfooterPesoRedStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterPesoRedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterPesoRedStyle.setAlignment(HorizontalAlignment.RIGHT); 
		
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
		redStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00"));
		redStyle.setFont(redFont);
		
		CellStyle redMoneyStyle = workbook.createCellStyle();
		redMoneyStyle.setBorderBottom(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderTop(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderRight(BorderStyle.MEDIUM);
		redMoneyStyle.setBorderLeft(BorderStyle.MEDIUM);
		redMoneyStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		redMoneyStyle.setFont(redFont);

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
			
		//FORMATO CUERPO 
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
		

		
		reporte.createCell(0).setCellValue(titulo);
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
		header.createCell(2).setCellValue("PRODUCTO");
		header.createCell(3).setCellValue("KGs.");
		header.createCell(4).setCellValue("P.U. NETO");
		header.createCell(5).setCellValue("PRECIO TOTAL");
		header.createCell(6).setCellValue("DEBITO FISCAL");
		header.createCell(7).setCellValue("COSTO UNITARIO");
		header.createCell(8).setCellValue("COSTO TOTAL");
		header.createCell(9).setCellValue("GANANCIA BRUTA");
		
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		header.getCell(5).setCellStyle(theaderStyle);
		header.getCell(6).setCellStyle(theaderStyle);
		header.getCell(7).setCellStyle(theaderStyle);
		header.getCell(8).setCellStyle(theaderStyle);
		header.getCell(9).setCellStyle(theaderStyle);
				
		Integer rownumber = 6;
		Row fila = sheet.createRow(rownumber);
		Cell cell = null;
		
	  // for(int i = 0; i < documentos.size() ; i++) {
	    for(Documento doc: documentos) {
		   
		  for(int i = 0; i < doc.getLineas().size() ; i++ ) { 
			
			//FECHA
			cell = fila.createCell(0);
			cell.setCellStyle(tbodydateStyle);
			//CLIENTE
			cell = fila.createCell(1);
			cell.setCellStyle(tbodyStyle);
			//PRODUCTO
			cell = fila.createCell(2);
			cell.setCellStyle(tbodyStyle);
			//KILO
			cell = fila.createCell(3);
			tbodyStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00"));
			cell.setCellStyle(tbodyStyle);
			//NRO. DOC
			cell = fila.createCell(4);
			cell.setCellStyle(tbodymoneyStyle);
			//NETO
			cell = fila.createCell(5);
			cell.setCellStyle(tbodymoneyStyle);
			//IVA
			cell = fila.createCell(6);
			cell.setCellStyle(tbodymoneyStyle);
			//TOTAL
			cell = fila.createCell(7);
			cell.setCellStyle(tbodymoneyStyle);
			
			cell = fila.createCell(8);
			cell.setCellStyle(tbodymoneyStyle);
			
			cell = fila.createCell(9);
			cell.setCellStyle(tbodymoneyStyle);
			
			rownumber = rownumber+ 1;
			fila = sheet.createRow(rownumber);
			
		   }
		}
		
		BigDecimal totalKgs = BigDecimal.valueOf(0);
		BigDecimal totalPrecio = BigDecimal.valueOf(0);
		BigDecimal totalDebitoFiscal = BigDecimal.valueOf(0);
 
		
		documentos.sort(Comparator.comparing(Documento::getFecha));
		FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
		rownumber = 6;
		
		
		for(Documento doc: documentos) {
		  
		  for(Item linea: doc.getLineas()) {
			  
			  fila = sheet.getRow(rownumber ++);
			  
			  cell = fila.getCell(0); 
			  cell.setCellValue(doc.getFecha());
			  
			  cell = fila.getCell(1);  
			  cell.setCellValue(doc.getCliente().getRazonsocial());
			  
			  cell = fila.getCell(2);  
			  cell.setCellValue(linea.getProducto().getNombre());

			  //PRECIO UNITARIO
			  cell = fila.getCell(4);  
			  cell.setCellValue(linea.getPrecio().doubleValue());
			  
			  if((!doc.getDocumento().equals("Nota de Crédito")) && (!doc.getDocumento().equals("Orden de Crédito Ventas"))) {
				  //KGs.
				  cell = fila.getCell(3);  
				  cell.setCellValue(linea.getCantidad().doubleValue());
				  
				  //PRECIO TOTAL
				  cell = fila.getCell(5);  
				  cell.setCellValue(linea.calcularImporteNeto().doubleValue());
				
				  //DEBITO FISCAL
				  cell = fila.getCell(6);
				  if(doc.getDocumento().equals("Presupuesto de Venta")) {
					  cell.setCellValue(0);
				  } else {
					  cell.setCellValue(linea.calcularImporteNeto().doubleValue() * 0.21);
					  totalDebitoFiscal = totalDebitoFiscal .add(linea.calcularImporteNeto() .multiply( BigDecimal.valueOf(0.21)));
				  }
				  
				  totalKgs = totalKgs .add( linea.getCantidad());
				  totalPrecio = totalPrecio.add(linea.calcularImporteNeto());
	 			  
		
				  
			  } else {
				  //KGs.
				  cell = fila.getCell(3);  
				  cell.setCellValue(-linea.getCantidad().doubleValue());
				  cell.setCellStyle(redStyle);
				  //PRECIO TOTAL
				  cell = fila.getCell(5);  
				  cell.setCellValue(-linea.calcularImporteNeto().doubleValue());
				  cell.setCellStyle(redMoneyStyle);
				//DEBITO FISCAL
				  cell = fila.getCell(6);  
				  if(doc.getDocumento().equals("Orden de Crédito Ventas")) {
					  cell.setCellValue(0);
				  } else {
					  cell.setCellValue(-linea.calcularImporteNeto().doubleValue() * 0.21);
					  totalDebitoFiscal = totalDebitoFiscal .subtract(linea.calcularImporteNeto() .multiply( BigDecimal.valueOf(0.21))).setScale(2, RoundingMode.HALF_EVEN);
					  cell.setCellStyle(redMoneyStyle);
				  }
			
				  totalKgs = totalKgs .subtract( linea.getCantidad()).setScale(2, RoundingMode.HALF_EVEN);
				  totalPrecio = totalPrecio.subtract(linea.calcularImporteNeto()).setScale(2, RoundingMode.HALF_EVEN);
	 			  
				  
			  }
			  	Cell ctoTotalCell = fila.getCell(8); 
			  	String formulaCtoTotal = "D".concat(rownumber.toString() +"*H" + rownumber.toString());
			  	ctoTotalCell.setCellFormula(formulaCtoTotal); 
			  	formulaEvaluator.evaluateFormulaCell(ctoTotalCell);
			  	
			  	Cell gananciaBrutalCell = fila.getCell(9); 
			  	String formulaGananciaBruta = "F".concat(rownumber.toString() +"-I" + rownumber.toString());
			  	gananciaBrutalCell.setCellFormula(formulaGananciaBruta); 
			  	formulaEvaluator.evaluateFormulaCell(gananciaBrutalCell);
			  	 
			 
 		  }
 
		  
	   }
		
		
		/*
		if(importeGastoMensual.doubleValue() >= 0) {
			cell.setCellStyle(tbodymoneyStyle);
		} else {
			cell.setCellStyle(redStyle);
		}
		*/
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
		cell.setCellValue(totalKgs.doubleValue()); 
		
		if(totalKgs.doubleValue() >= .0) {
			cell.setCellStyle(tfooterPesoStyle);
		} else {
			 
			cell.setCellStyle(tfooterPesoRedStyle);
		}
		
		tfooterStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00")); 
		tfooterRedStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00")); 

		cell = filatotal.createCell(4);
		cell.setCellStyle(tfooterCenterStyle);
		
		cell = filatotal.createCell(5);
		cell.setCellValue(totalPrecio.doubleValue());
		if(totalPrecio.doubleValue() >= .0) {
			 
			cell.setCellStyle(tfooterStyle);
		} else {
			cell.setCellStyle(tfooterRedStyle);
		}
 
		cell = filatotal.createCell(6);
		cell.setCellValue(totalDebitoFiscal.doubleValue());
		if(totalDebitoFiscal.doubleValue() >= .0) {
			 
			cell.setCellStyle(tfooterStyle);
		} else {
			cell.setCellStyle(tfooterRedStyle);
		}
 
		
		cell = filatotal.createCell(7);
		cell.setCellStyle(theaderStyle);
		
		
		cell = filatotal.createCell(8);
		cell.setCellStyle(tfooterStyle);
		
	  	String costoTotal = "SUM(I7:I".concat(rownumber.toString() +")");
	  	cell.setCellFormula(costoTotal); 
	  	formulaEvaluator.evaluateFormulaCell(cell);
	  	

		
		cell = filatotal.createCell(9);
		cell.setCellStyle(tfooterStyle);
		
	  	String gananciaTotal = "SUM(J7:J" + rownumber.toString() +")";
	  	cell.setCellFormula(gananciaTotal); 
	  	formulaEvaluator.evaluateFormulaCell(cell);

				 
		sheet.setColumnWidth(0, 10 * 256);
		sheet.setColumnWidth(1, 20 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(9);
		
		sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.LEGAL_PAPERSIZE);
		sheet.getPrintSetup().setLandscape(true);
		
	}
	

}
