package com.recicladosplasticos.springboot.app.view.xlsx;

 
import java.text.DecimalFormat;
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
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.recicladosplasticos.springboot.app.models.entity.ItemVentasPresupuesto;
import com.recicladosplasticos.springboot.app.models.entity.VentasPresupuesto;

@Component("/ventas/presupuesto/ver.xlsx")
public class VentasPresupuestoXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 
		
		  response.setHeader("Content-Disposition", "attachment; filename=\"ordenes_de_entrega_view.xlsx\" ");
		  
		  VentasPresupuesto ordenentrega = (VentasPresupuesto) model.get("presupuesto");
		  
		  String nombreArchivo = "presupuesto_de_venta_" + ordenentrega.getLetra() + "_" + ordenentrega.getPrefijo() + "-" + ordenentrega.getNumero().toString() + ".xlsx";
		  response.setHeader("Content-Disposition", "attachment; filename=\"".concat(nombreArchivo) + "\"");

		  	Boolean descuento = false;
		  
		  /*	
			for (ItemOrdenEntrega item : ordenentrega.getItems()) {

				if( item.getDescuento().compareTo(BigDecimal.valueOf(0)) > 0)  descuento = true; 
			}
		  */
 
		  
		  Sheet sheet = workbook.createSheet("Presupuesto");
		  

		  DecimalFormat df = new DecimalFormat("00000000");
		  
		  
		  Row row = sheet.createRow(0);
		  
		  Cell cell = row.createCell(0);
		  Cell empresa = row.createCell(1);
		  empresa.setCellValue("RECICLADOS PLASTICOS"); 
		  CellStyle empresaStyle = workbook.createCellStyle(); 
		  Font empresaFont= workbook.createFont();
		  empresaFont.setBold(true); 
		  empresaFont.setFontHeightInPoints((short) 18 );
		  empresaStyle.setFont(empresaFont); 
		  empresa.setCellStyle(empresaStyle);
		  
		  
			Cell documento = row.createCell(2);
			documento.setCellValue("PRESUPUESTO DE VENTA");
			CellStyle boldStyle = workbook.createCellStyle();
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);
			boldStyle.setFont(boldFont);
			documento.setCellStyle(boldStyle);
		  
			Row row1 = sheet.createRow(1);
			Cell direccion = row1.createCell(1);
			direccion.setCellValue("31 Y 513 (1903) José Hernández - La Plata");
			direccion.setCellStyle(boldStyle);

			Row row2 = sheet.createRow(2);
			Cell telefono = row2.createCell(1);
			telefono.setCellValue("T.E. (011) 6991-2099 / (011) 6761-7903");
			telefono.setCellStyle(boldStyle);

			Cell fecha = row1.createCell(2);
			fecha.setCellValue("FECHA");
			fecha.setCellStyle(boldStyle);

			Cell fechaData = row1.createCell(3);
			CreationHelper createHelper = workbook.getCreationHelper();
			CellStyle fechaDataStyle = workbook.createCellStyle();
			fechaDataStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
			fechaData.setCellValue(ordenentrega.getFecha());
			fechaDataStyle.setFont(boldFont);
			fechaData.setCellStyle(fechaDataStyle);

			Cell numero = row2.createCell(2);
			numero.setCellValue("Número");
			numero.setCellStyle(boldStyle);

			Cell numeroData = row2.createCell(3);
			numeroData.setCellValue(ordenentrega.getPrefijo() + "-" + df.format(ordenentrega.getNumero()));
			numeroData.setCellStyle(boldStyle);

			Row row4 = sheet.createRow(3);
			Cell cliente = row4.createCell(0);
			cliente.setCellValue("CLIENTE:");
			cliente.setCellStyle(boldStyle);

			Cell clienteData = row4.createCell(1);
			clienteData.setCellValue("(" + ordenentrega.getCliente().getCodigo() + ") " + ordenentrega.getCliente().getNombre());
			clienteData.setCellStyle(boldStyle);

			Row row5 = sheet.createRow(4);
			Cell dirCliente = row5.createCell(0);
			dirCliente.setCellValue("DIRECCIÓN:");
			dirCliente.setCellStyle(boldStyle);

			Cell dirClienteData = row5.createCell(1);
			dirClienteData
					.setCellValue(ordenentrega.getCliente().getDireccion() + " CP:" + ordenentrega.getCliente().getCodigopostal()
							+ ", " + ordenentrega.getCliente().getLocalidad() + " " + ordenentrega.getCliente().getProvincia());
			
			
			
			CellStyle direccionStyle = workbook.createCellStyle(); 
			Font direccionFont= workbook.createFont();
			direccionFont.setFontHeightInPoints((short) 11 );
			direccionStyle.setFont(direccionFont); 
			dirClienteData.setCellStyle(direccionStyle);
			

			CellStyle theaderStyle = workbook.createCellStyle();
			theaderStyle.setFont(boldFont);

			theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
			theaderStyle.setBorderTop(BorderStyle.MEDIUM);
			theaderStyle.setBorderRight(BorderStyle.MEDIUM);
			theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
			theaderStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
			theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			theaderStyle.setAlignment(HorizontalAlignment.CENTER);

			CellStyle tbodyStyle = workbook.createCellStyle();
			
			Font tbodyFont= workbook.createFont();
			tbodyFont.setFontHeightInPoints((short) 11 );
			tbodyStyle.setFont(tbodyFont);
			  
			tbodyStyle.setBorderBottom(BorderStyle.MEDIUM);
			tbodyStyle.setBorderTop(BorderStyle.MEDIUM);
			tbodyStyle.setBorderRight(BorderStyle.MEDIUM);
			tbodyStyle.setBorderLeft(BorderStyle.MEDIUM);

			CellStyle tbodyqtyStyle = workbook.createCellStyle();
			
			Font tbodyqtyFont= workbook.createFont();
			tbodyqtyFont.setFontHeightInPoints((short) 11 );
			tbodyqtyStyle.setFont(tbodyqtyFont);
			
			
			tbodyqtyStyle.setBorderBottom(BorderStyle.MEDIUM);
			tbodyqtyStyle.setBorderTop(BorderStyle.MEDIUM);
			tbodyqtyStyle.setBorderRight(BorderStyle.MEDIUM);
			tbodyqtyStyle.setBorderLeft(BorderStyle.MEDIUM);
			DataFormat format = workbook.createDataFormat();
			tbodyqtyStyle.setDataFormat(format.getFormat("###,###,##0.00"));

			CellStyle tbodymoneyStyle = workbook.createCellStyle();
			
			Font tbodymoneyFont= workbook.createFont();
		    tbodymoneyFont.setFontHeightInPoints((short) 11 );
			tbodymoneyStyle.setFont(tbodymoneyFont);
			 
			tbodymoneyStyle.setBorderBottom(BorderStyle.MEDIUM);
			tbodymoneyStyle.setBorderTop(BorderStyle.MEDIUM);
			tbodymoneyStyle.setBorderRight(BorderStyle.MEDIUM);
			tbodymoneyStyle.setBorderLeft(BorderStyle.MEDIUM);

			tbodymoneyStyle.setDataFormat(format.getFormat("$###,###,##0.00"));

			Row header = sheet.createRow(5);
			header.createCell(0).setCellValue("CANTIDAD");
			header.createCell(1).setCellValue("DESCRIPCION");
			header.createCell(2).setCellValue("UNIT.");
			
			if(descuento) {
				header.createCell(3).setCellValue("DESC.");
				header.createCell(4).setCellValue("TOTAL");
			} else {
				header.createCell(3).setCellValue("TOTAL");
			}
			
			header.getCell(0).setCellStyle(theaderStyle);
			header.getCell(1).setCellStyle(theaderStyle);
			header.getCell(2).setCellStyle(theaderStyle);
			header.getCell(3).setCellStyle(theaderStyle);
	
			if(descuento) header.getCell(4).setCellStyle(theaderStyle);


			int rownumber = 6;
			Row fila = sheet.createRow(rownumber);

			for (int i = 0; i < 6; i++) {

				cell = fila.createCell(0);
				cell.setCellStyle(tbodyqtyStyle);
				cell = fila.createCell(1);
				cell.setCellStyle(tbodyStyle);
				cell = fila.createCell(2);
				cell.setCellStyle(tbodymoneyStyle);
				
				if(descuento) {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodyqtyStyle);
					cell = fila.createCell(4);
					cell.setCellStyle(tbodymoneyStyle);
				} else {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodymoneyStyle);
				}

				rownumber = rownumber + 1;
				fila = sheet.createRow(rownumber);

			}

			Row filatotal = sheet.createRow(rownumber);

			if(descuento) {
				cell = filatotal.createCell(4);
				cell.setCellStyle(tbodymoneyStyle);
			} else {
				cell = filatotal.createCell(3);
				cell.setCellStyle(tbodymoneyStyle);
			}
			
		 	rownumber = 6;
			for (ItemVentasPresupuesto item : ordenentrega.getItems()) {

				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(item.getCantidad().doubleValue());

				cell = fila.getCell(1);
				  
				cell.setCellValue(item.getProducto().getUnidad().getNombre()  + " " + item.getProducto().getNombre());

				cell = fila.getCell(2);
				cell.setCellValue(item.getPrecio().doubleValue());
				
				if(descuento) {
					cell = fila.getCell(3);
					cell.setCellValue(item.getDescuento().doubleValue());
					cell = fila.getCell(4);
					cell.setCellValue(item.getImporteNeto().doubleValue());
				} else {
					cell = fila.getCell(3);
					cell.setCellValue(item.getImporteNeto().doubleValue());
				}


			}
			
			//SOLO SI ES ORDEN DETALLE
			if(ordenentrega.getDetalle() != null) {
				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(1);

				cell = fila.getCell(1);
				cell.setCellValue(ordenentrega.getDetalle());

				cell = fila.getCell(2);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
				
				cell = fila.getCell(3);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
		 
			}
			
			
			
			if(descuento) {
				cell = filatotal.getCell(4);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
			} else {
				cell = filatotal.getCell(3);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
			}

		 
			Cell cellObservacion = sheet.getRow(12).createCell(0);
			cellObservacion.setCellValue("Observacion:".concat(ordenentrega.getObservacion()));

			row = sheet.createRow(14);
			cell = row.createCell(0);
			CellStyle lineStyle = workbook.createCellStyle();
			lineStyle.setBorderTop(BorderStyle.DASH_DOT);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(1);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(2);
			cell.setCellStyle(lineStyle);
			
			if(descuento) {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
				cell = row.createCell(4);
				cell.setCellStyle(lineStyle);
			} else {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
			}
			
 

			/// SEGUNDA COPIA

			row = sheet.createRow(15);
			cell = row.createCell(0);
			empresa = row.createCell(1);
			empresa.setCellValue("RECICLADOS PLASTICOS");
			empresa.setCellStyle(empresaStyle);

			documento = row.createCell(2);
			documento.setCellValue("PRESUPUESTO DE VENTA");
			documento.setCellStyle(boldStyle);

			row1 = sheet.createRow(16);
			direccion = row1.createCell(1);
			direccion.setCellValue("31 Y 513 (1903) José Hernández - La Plata");
			direccion.setCellStyle(boldStyle);

			row2 = sheet.createRow(17);
			telefono = row2.createCell(1);
			telefono.setCellValue("T.E. (011) 6991-2099 / (011) 6761-7903");
			telefono.setCellStyle(boldStyle);

			fecha = row1.createCell(2);
			fecha.setCellValue("FECHA");
			fecha.setCellStyle(boldStyle);

			fechaData = row1.createCell(3);
			fechaData.setCellStyle(fechaDataStyle);
			fechaData.setCellValue(ordenentrega.getFecha());

			numero = row2.createCell(2);
			numero.setCellValue("NUMERO");
			numero.setCellStyle(boldStyle);

			numeroData = row2.createCell(3);
			numeroData.setCellValue(ordenentrega.getPrefijo() + "-" + df.format(ordenentrega.getNumero()));
			numeroData.setCellStyle(boldStyle);

			row4 = sheet.createRow(18);
			cliente = row4.createCell(0);
			cliente.setCellValue("CLIENTE:");
			cliente.setCellStyle(boldStyle);

			clienteData = row4.createCell(1);
			clienteData.setCellValue("(" + ordenentrega.getCliente().getCodigo() + ") " + ordenentrega.getCliente().getNombre());
			clienteData.setCellStyle(boldStyle);

			row5 = sheet.createRow(19);
			dirCliente = row5.createCell(0);
			dirCliente.setCellValue("DIRECCIÓN:");
			dirCliente.setCellStyle(boldStyle);

			dirClienteData = row5.createCell(1);
			dirClienteData
					.setCellValue(ordenentrega.getCliente().getDireccion() + " CP:" + ordenentrega.getCliente().getCodigopostal()
							+ ", " + ordenentrega.getCliente().getLocalidad() + " " + ordenentrega.getCliente().getProvincia());
			
            dirClienteData.setCellStyle(direccionStyle);

			header = sheet.createRow(20);
			header.createCell(0).setCellValue("CANTIDAD");
			header.createCell(1).setCellValue("DESCRIPCION");
			header.createCell(2).setCellValue("UNIT.");
			
			if(descuento) {
				header.createCell(3).setCellValue("DESC.");
				header.createCell(4).setCellValue("TOTAL");
			} else {
				header.createCell(3).setCellValue("TOTAL");
			}
			 

			header.getCell(0).setCellStyle(theaderStyle);
			header.getCell(1).setCellStyle(theaderStyle);
			header.getCell(2).setCellStyle(theaderStyle);
			header.getCell(3).setCellStyle(theaderStyle);
			
			if(descuento) header.getCell(4).setCellStyle(theaderStyle);

			rownumber = 21;
			fila = sheet.createRow(rownumber);

			for (int i = 0; i < 6; i++) {

				cell = fila.createCell(0);
				cell.setCellStyle(tbodyqtyStyle);
				cell = fila.createCell(1);
				cell.setCellStyle(tbodyStyle);
				cell = fila.createCell(2);
				cell.setCellStyle(tbodymoneyStyle);
  
				
				if(descuento) {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodyqtyStyle);
					cell = fila.createCell(4);
					cell.setCellStyle(tbodymoneyStyle);
				} else {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodymoneyStyle);
				}

				rownumber = rownumber + 1;
				fila = sheet.createRow(rownumber);

			}

			filatotal = sheet.createRow(rownumber);
			
			if(descuento) {
				cell = filatotal.createCell(4);
				cell.setCellStyle(tbodymoneyStyle);
			} else {
				cell = filatotal.createCell(3);
				cell.setCellStyle(tbodymoneyStyle);
			}

			rownumber = 21;
			for (ItemVentasPresupuesto item : ordenentrega.getItems()) {

				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(item.getCantidad().doubleValue());

				cell = fila.getCell(1);
				cell.setCellValue(item.getProducto().getUnidad().getNombre()  + " " + item.getProducto().getNombre());

				cell = fila.getCell(2);
				cell.setCellValue(item.getPrecio().doubleValue());

				if(descuento) {
					cell = fila.getCell(3);
					cell.setCellValue(item.getDescuento().doubleValue());
					cell = fila.getCell(4);
					cell.setCellValue(item.getImporteNeto().doubleValue());
				} else {
					cell = fila.getCell(3);
					cell.setCellValue(item.getImporteNeto().doubleValue());
				}

			}
			
			//SOLO SI ES ORDEN DETALLE
			if(ordenentrega.getDetalle() != null) {
				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(1);

				cell = fila.getCell(1);
				cell.setCellValue(ordenentrega.getDetalle());

				cell = fila.getCell(2);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
				
				cell = fila.getCell(3);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
		 
			}

			if(descuento) {
				cell = filatotal.getCell(4);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
			} else {
				cell = filatotal.getCell(3);
				cell.setCellValue(ordenentrega.getImporte().doubleValue());
			}
			
			cellObservacion = sheet.getRow(27).createCell(0);
			cellObservacion.setCellValue("Observacion:".concat(ordenentrega.getObservacion()));

			row = sheet.createRow(29);
			cell = row.createCell(0);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(1);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(2);
			cell.setCellStyle(lineStyle);
 
			
			if(descuento) {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
				cell = row.createCell(4);
				cell.setCellStyle(lineStyle);
			} else {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
			}

			// TERCER COPIA

			row = sheet.createRow(30);
			cell = row.createCell(0);
			empresa = row.createCell(1);
			empresa.setCellValue("RECICLADOS PLASTICOS");
			empresa.setCellStyle(empresaStyle);

			documento = row.createCell(2);
			documento.setCellValue("PRESUPUESTO DE VENTA");
			documento.setCellStyle(boldStyle);

			row1 = sheet.createRow(31);
			direccion = row1.createCell(1);
			direccion.setCellValue("31 Y 513 (1903) José Hernández - La Plata");
			direccion.setCellStyle(boldStyle);

			row2 = sheet.createRow(32);
			telefono = row2.createCell(1);
			telefono.setCellValue("T.E. (011) 6991-2099 / (011) 6761-7903");
			telefono.setCellStyle(boldStyle);

			fecha = row1.createCell(2);
			fecha.setCellValue("FECHA");
			fecha.setCellStyle(boldStyle);

			fechaData = row1.createCell(3);
			fechaData.setCellStyle(fechaDataStyle);
			fechaData.setCellValue(ordenentrega.getFecha());

			numero = row2.createCell(2);
			numero.setCellValue("NUMERO");
			numero.setCellStyle(boldStyle);

			numeroData = row2.createCell(3);
			numeroData.setCellValue(ordenentrega.getPrefijo() + "-" + df.format(ordenentrega.getNumero()));
			numeroData.setCellStyle(boldStyle);

			row4 = sheet.createRow(33);
			cliente = row4.createCell(0);
			cliente.setCellValue("CLIENTE:");
			cliente.setCellStyle(boldStyle);

			clienteData = row4.createCell(1);
			clienteData.setCellValue("(" + ordenentrega.getCliente().getCodigo() + ") " + ordenentrega.getCliente().getNombre());
			clienteData.setCellStyle(boldStyle);

			row5 = sheet.createRow(34);
			dirCliente = row5.createCell(0);
			dirCliente.setCellValue("DIRECCIÓN:");
			dirCliente.setCellStyle(boldStyle);

			dirClienteData = row5.createCell(1);
			dirClienteData
					.setCellValue(ordenentrega.getCliente().getDireccion() + " CP:" + ordenentrega.getCliente().getCodigopostal()
							+ ", " + ordenentrega.getCliente().getLocalidad() + " " + ordenentrega.getCliente().getProvincia());
			
			dirClienteData.setCellStyle(direccionStyle);

			header = sheet.createRow(35);
			header.createCell(0).setCellValue("CANTIDAD");
			header.createCell(1).setCellValue("DESCRIPCION");
			header.createCell(2).setCellValue("UNIT.");
			
			if(descuento) {
				header.createCell(3).setCellValue("DESC.");
				header.createCell(4).setCellValue("TOTAL");
			} else {
				header.createCell(3).setCellValue("TOTAL");
			}
			 

			header.getCell(0).setCellStyle(theaderStyle);
			header.getCell(1).setCellStyle(theaderStyle);
			header.getCell(2).setCellStyle(theaderStyle);
			header.getCell(3).setCellStyle(theaderStyle);
			
			if(descuento) header.getCell(4).setCellStyle(theaderStyle);

			rownumber = 36;
			fila = sheet.createRow(rownumber);

			for (int i = 0; i < 6; i++) {

				cell = fila.createCell(0);
				cell.setCellStyle(tbodyqtyStyle);
				cell = fila.createCell(1);
				cell.setCellStyle(tbodyStyle);
				cell = fila.createCell(2);
				cell.setCellStyle(tbodyStyle);
				
				if(descuento) {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodyqtyStyle);
					cell = fila.createCell(4);
					cell.setCellStyle(tbodymoneyStyle);
				} else {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodymoneyStyle);
				}
 

				rownumber = rownumber + 1;
				fila = sheet.createRow(rownumber);

			}

			filatotal = sheet.createRow(rownumber);
			
			if(descuento) {
				cell = filatotal.createCell(4);
				cell.setCellStyle(tbodymoneyStyle);
			} else {
				cell = filatotal.createCell(3);
				cell.setCellStyle(tbodymoneyStyle);
			}
 

			rownumber = 36;
			for (ItemVentasPresupuesto item : ordenentrega.getItems()) {

				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(item.getCantidad().doubleValue());

				cell = fila.getCell(1);
				cell.setCellValue(item.getProducto().getUnidad().getNombre()  + " " +item.getProducto().getNombre());

			}
			
			//SOLO SI ES ORDEN DETALLE
			if(ordenentrega.getDetalle() != null) {
				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(1);

				cell = fila.getCell(1);
				cell.setCellValue(ordenentrega.getDetalle());
  			}

			cellObservacion = sheet.getRow(42).createCell(0);
			cellObservacion.setCellValue("Observacion:".concat(ordenentrega.getObservacion()));
			
			row = sheet.createRow(44);
			cell = row.createCell(0);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(1);
			cell.setCellStyle(lineStyle);
			cell = row.createCell(2);
			cell.setCellStyle(lineStyle);

			if(descuento) {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
				cell = row.createCell(4);
				cell.setCellStyle(lineStyle);
			} else {
				cell = row.createCell(3);
				cell.setCellStyle(lineStyle);
			}
			

			// CUARTA COPIA

			row = sheet.createRow(45);
			cell = row.createCell(0);
			empresa = row.createCell(1);
			empresa.setCellValue("RECICLADOS PLASTICOS");
			empresa.setCellStyle(empresaStyle);

			documento = row.createCell(2);
			documento.setCellValue("PRESUPUESTO DE VENTA");
			documento.setCellStyle(boldStyle);

			row1 = sheet.createRow(46);
			direccion = row1.createCell(1);
			direccion.setCellValue("31 Y 513 (1903) José Hernández - La Plata");
			direccion.setCellStyle(boldStyle);

			row2 = sheet.createRow(47);
			telefono = row2.createCell(1);
			telefono.setCellValue("T.E. (011) 6991-2099 / (011) 6761-7903");
			telefono.setCellStyle(boldStyle);

			fecha = row1.createCell(2);
			fecha.setCellValue("FECHA");
			fecha.setCellStyle(boldStyle);

			fechaData = row1.createCell(3);
			fechaData.setCellStyle(fechaDataStyle);
			fechaData.setCellValue(ordenentrega.getFecha());

			numero = row2.createCell(2);
			numero.setCellValue("NUMERO");
			numero.setCellStyle(boldStyle);

			numeroData = row2.createCell(3);
			numeroData.setCellValue(ordenentrega.getPrefijo() + "-" + df.format(ordenentrega.getNumero()));
			numeroData.setCellStyle(boldStyle);

			row4 = sheet.createRow(48);
			cliente = row4.createCell(0);
			cliente.setCellValue("CLIENTE:");
			cliente.setCellStyle(boldStyle);

			clienteData = row4.createCell(1);
			clienteData.setCellValue("(" + ordenentrega.getCliente().getCodigo() + ") " + ordenentrega.getCliente().getNombre());
			clienteData.setCellStyle(boldStyle);

			row5 = sheet.createRow(49);
			dirCliente = row5.createCell(0);
			dirCliente.setCellValue("DIRECCIÓN:");
			dirCliente.setCellStyle(boldStyle);

			dirClienteData = row5.createCell(1);
			dirClienteData
					.setCellValue(ordenentrega.getCliente().getDireccion() + " CP:" + ordenentrega.getCliente().getCodigopostal()
							+ ", " + ordenentrega.getCliente().getLocalidad() + " " + ordenentrega.getCliente().getProvincia());
			
			dirClienteData.setCellStyle(direccionStyle);

			header = sheet.createRow(50);
			header.createCell(0).setCellValue("CANTIDAD");
			header.createCell(1).setCellValue("DESCRIPCION");
			header.createCell(2).setCellValue("UNIT.");
		
			if(descuento) {
				header.createCell(3).setCellValue("DESC.");
				header.createCell(4).setCellValue("TOTAL");
			} else {
				header.createCell(3).setCellValue("TOTAL");
			}
			 

			header.getCell(0).setCellStyle(theaderStyle);
			header.getCell(1).setCellStyle(theaderStyle);
			header.getCell(2).setCellStyle(theaderStyle);
			header.getCell(3).setCellStyle(theaderStyle);
			
			if(descuento) header.getCell(4).setCellStyle(theaderStyle);

			rownumber = 51;
			fila = sheet.createRow(rownumber);

			for (int i = 0; i < 6; i++) {

				cell = fila.createCell(0);
				cell.setCellStyle(tbodyqtyStyle);
				cell = fila.createCell(1);
				cell.setCellStyle(tbodyStyle);
				cell = fila.createCell(2);
				cell.setCellStyle(tbodyStyle);
 
				if(descuento) {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodyqtyStyle);
					cell = fila.createCell(4);
					cell.setCellStyle(tbodymoneyStyle);
				} else {
					cell = fila.createCell(3);
					cell.setCellStyle(tbodymoneyStyle);
				}

				rownumber = rownumber + 1;
				fila = sheet.createRow(rownumber);

			}

			filatotal = sheet.createRow(rownumber);
		
			if(descuento) {
				cell = filatotal.createCell(4);
				cell.setCellStyle(tbodymoneyStyle);
			} else {
				cell = filatotal.createCell(3);
				cell.setCellStyle(tbodymoneyStyle);
			}
  		

			rownumber = 51;
			for (ItemVentasPresupuesto item : ordenentrega.getItems()) {

				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(item.getCantidad().doubleValue());

				cell = fila.getCell(1);
				cell.setCellValue(item.getProducto().getUnidad().getNombre()  + " " + item.getProducto().getNombre());

			}
			
			//SOLO SI ES ORDEN DETALLE
			if(ordenentrega.getDetalle() != null) {
				fila = sheet.getRow(rownumber++);

				cell = fila.getCell(0);
				cell.setCellValue(1);

				cell = fila.getCell(1);
				cell.setCellValue(ordenentrega.getDetalle());
  			}

			cellObservacion = sheet.getRow(57).createCell(0);
			cellObservacion.setCellValue("Observacion:".concat(ordenentrega.getObservacion()));
			
			row = sheet.createRow(58);
			Cell firma = row.createCell(1);
			firma.setCellValue("FIRMA");
			CellStyle firmaStyle = workbook.createCellStyle();
			Font firmaFont = workbook.createFont();
			firmaFont.setBold(true);
			firmaStyle.setFont(firmaFont);
			firmaStyle.setAlignment(HorizontalAlignment.CENTER);
			firma.setCellStyle(firmaStyle);

			
			
	
			if(descuento) {
				sheet.setColumnWidth(0, 10 * 256); 
				sheet.setColumnWidth(1, 38 * 256);
				sheet.setColumnWidth(2, 10 * 256);  
				sheet.setColumnWidth(3, 10 * 256);
				sheet.setColumnWidth(4, 10 * 256);
				
			} else {
				sheet.setColumnWidth(0, 12 * 256);  
				sheet.setColumnWidth(1, 38 * 256);
				sheet.setColumnWidth(2, 12 * 256);  
				sheet.setColumnWidth(3, 14 * 256);
			 	
			}
			
		 
	 
			//double leftMarginInches = sheet.getMargin(Sheet.LeftMargin);
			sheet.setMargin(Sheet.RightMargin, 0.0 );
			sheet.setMargin(Sheet.LeftMargin, 0.1 );
			
			sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.LEGAL_PAPERSIZE);
			
			
		 
		
	}

}
