package com.recicladosplasticos.springboot.app.view.xlsx;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.recicladosplasticos.springboot.app.models.entity.VentasRemito;

@Component("/ventas/remito/exportar.xlsx")
public class VentasRemitoMensualXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 response.setHeader("Content-Disposition", "attachment; filename=\"listado_remitos.xlsx\" ");
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		 

		

		@SuppressWarnings("unchecked")
		List<VentasRemito> ventasRemitos =   (List<VentasRemito>) model.get("remitos");
		
		 Date fechainicio =   (Date) model.get("fechainicio");
		 Date fechafin =  (Date)  model.get("fechafin");
			
		Sheet sheet = workbook.createSheet("Remitos");
		DecimalFormat df = new DecimalFormat("00000000");
		 

		
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
		
		//FORMATO CUERPO FECHA
		CellStyle tbodydateStyle = workbook.createCellStyle();
		tbodydateStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderLeft(BorderStyle.MEDIUM);
		CreationHelper createHelper = workbook.getCreationHelper();
		tbodydateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
			
		//FORMATO CUERPO CLIENTE -
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyStyle.setBorderLeft(BorderStyle.MEDIUM);
			
		//FORMATO CUERPO  
		DataFormat format = workbook.createDataFormat();
		CellStyle tbodyNumberStyle = workbook.createCellStyle();
		tbodyNumberStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyNumberStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyNumberStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyNumberStyle.setBorderLeft(BorderStyle.MEDIUM);
		tbodyNumberStyle.setDataFormat(format.getFormat("###,###,###,###,##0.00"));

		CellStyle tbodyNumberLinesStyle = workbook.createCellStyle();
		tbodyNumberLinesStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyNumberLinesStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyNumberLinesStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyNumberLinesStyle.setBorderLeft(BorderStyle.MEDIUM);
		tbodyNumberLinesStyle.setDataFormat(format.getFormat("###,###,###,###,##0"));

		
		
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
		

		
		reporte.createCell(0).setCellValue("Reporte de ventas - Remitos");
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
		header.createCell(3).setCellValue("NUMERO");
		header.createCell(4).setCellValue("CANTIDAD LINEAS");
		header.createCell(5).setCellValue("CANTIDAD TOTAL");
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		header.getCell(5).setCellStyle(theaderStyle);
		
		int rownumber = 6;
		Row fila = sheet.createRow(rownumber);
		 Cell cell = null;
		
		for(int i = 0; i <   ventasRemitos.size() ; i++) {
		
			//FECHA
			cell = fila.createCell(0);
			cell.setCellStyle(tbodydateStyle);
			//CLIENTE
			cell = fila.createCell(1);
			cell.setCellStyle(tbodyStyle);
			//CUIT
			cell = fila.createCell(2);
			cell.setCellStyle(tbodyStyle);
			//NRO. DEV
			cell = fila.createCell(3);
			cell.setCellStyle(tbodyStyle);
			//LINEAS
			cell = fila.createCell(4);
			cell.setCellStyle(tbodyNumberLinesStyle);
			//TOTAL CANT
			cell = fila.createCell(5);
			cell.setCellStyle(tbodyNumberStyle);			
			rownumber = rownumber+ 1;
			fila = sheet.createRow(rownumber);
			
		}
		
		Double totalLineas = 0.0;
		Double totalCantidad = 0.0;
		
		rownumber = 6;
		for(VentasRemito ventasRemito: ventasRemitos) {
		  
		  fila = sheet.getRow(rownumber ++);
		  
		  cell = fila.getCell(0); 
		  cell.setCellValue(ventasRemito.getFecha());
		  
		  cell = fila.getCell(1);  
		  cell.setCellValue(ventasRemito.getCliente().getRazonsocial());
		  
		  cell = fila.getCell(2);  
		  cell.setCellValue(ventasRemito.getCliente().getCuit());
		   
		  cell = fila.getCell(3); 
		  cell.setCellValue(ventasRemito.getPrefijo()+"-"+ df.format(ventasRemito.getNumero()));
		 
		  cell = fila.getCell(4); 
		  cell.setCellValue(ventasRemito.getLineas().doubleValue());

		  cell = fila.getCell(5); 
		  cell.setCellValue(ventasRemito.getCantidadtotal().doubleValue());

		  totalLineas += ventasRemito.getLineas(); 
		  totalCantidad += ventasRemito.getCantidadtotal().doubleValue();
		 	  
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
		cell.setCellValue(totalLineas);
		theaderStyle.setDataFormat(format.getFormat("###,###,###,###,##0"));
		cell.setCellStyle(theaderStyle);
		cell = filatotal.createCell(5);
		cell.setCellValue(totalCantidad);
		theaderStyle.setDataFormat(format.getFormat("###,###,###,###,##0"));
		cell.setCellStyle(theaderStyle);
		 
		sheet.setColumnWidth(0, 12 * 256);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);

		
	}
	

}
