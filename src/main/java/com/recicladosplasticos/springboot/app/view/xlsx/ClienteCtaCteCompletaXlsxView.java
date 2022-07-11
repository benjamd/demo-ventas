package com.recicladosplasticos.springboot.app.view.xlsx;

import java.text.DecimalFormat;
import java.util.Comparator;

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

import com.recicladosplasticos.springboot.app.models.entity.Cliente;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeCredito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasRecibo;
import com.recicladosplasticos.springboot.app.models.entity.Documento;

@Component("/ventas/cliente/ctacte.xlsx")
public class ClienteCtaCteCompletaXlsxView extends AbstractXlsxView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachment; filename=\"cliente_cuenta_corriente.xlsx\" ");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		Cliente cliente = (Cliente) model.get("cliente");
		List<Documento> documentos = (List<Documento>) model.get("documentos");
		Sheet sheet = workbook.createSheet("Cuenta Corriente");
		DecimalFormat df = new DecimalFormat("00000000");
		// HEADER
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

		// FORMATO CUERPO FECHA
		CellStyle tbodydateStyle = workbook.createCellStyle();
		tbodydateStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodydateStyle.setBorderLeft(BorderStyle.MEDIUM);
		CreationHelper createHelper = workbook.getCreationHelper();
		tbodydateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		// FORMATO CUERPO CLIENTE - NRO. FACTURA
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.MEDIUM);
		tbodyStyle.setBorderTop(BorderStyle.MEDIUM);
		tbodyStyle.setBorderRight(BorderStyle.MEDIUM);
		tbodyStyle.setBorderLeft(BorderStyle.MEDIUM);

		// FORMATO CUERPO NETO - IVA - TOTAL
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
		tbodydateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		reporte.createCell(0).setCellValue("Cliente - Cuenta Corriente: (" + cliente.getCodigo() + ") "
				+ cliente.getNombre() + " - " + cliente.getRazonsocial());
		reporte.getCell(0).setCellStyle(tituloStyle);
		CellStyle fechaStyle = workbook.createCellStyle();
		fechaStyle.setFont(boldFont);
		fechaStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));

		Row header = sheet.createRow(5);
		header.createCell(0).setCellValue("FECHA");
		header.createCell(1).setCellValue("DOCUMENTO");
		header.createCell(2).setCellValue("NUMERO");
		header.createCell(3).setCellValue("DEBE");
		header.createCell(4).setCellValue("HABER");
		header.createCell(5).setCellValue("SALDO");
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		header.getCell(4).setCellStyle(theaderStyle);
		header.getCell(5).setCellStyle(theaderStyle);

		int rownumber = 6;
		Row fila = sheet.createRow(rownumber);
		Cell cell = null;
		Double debe = 0.0;
		Double haber = 0.0;
		Double saldo = 0.0;
		Double totalDebe = 0.0;
		Double totalHaber = 0.0;

		documentos.sort(Comparator.comparing(Documento::getFecha));

		for (Documento documento : documentos) {
			// FECHA
			cell = fila.createCell(0);
			cell.setCellStyle(tbodydateStyle);
			// DOC
			cell = fila.createCell(1);
			cell.setCellStyle(tbodyStyle);
			// NRO. DOC
			cell = fila.createCell(2);
			cell.setCellStyle(tbodyStyle);
			// DEBE
			cell = fila.createCell(3);
			cell.setCellStyle(tbodymoneyStyle);
			// HABER
			cell = fila.createCell(4);
			cell.setCellStyle(tbodymoneyStyle);
			// SALDO PENDIENTE
			cell = fila.createCell(5);
			cell.setCellStyle(tbodymoneyStyle);

			cell = fila.getCell(0);
			cell.setCellValue(documento.getFecha());
			cell = fila.getCell(1);
			String documentoYLetra = documento.getDocumento() + " ";
			if (documento.getLetra() != null)
				documentoYLetra.concat(documento.getLetra());
			cell.setCellValue(documentoYLetra);
			cell = fila.getCell(2);
			cell.setCellValue(documento.getPrefijo() + "-" + df.format(documento.getNumero()));
			cell = fila.getCell(3);
			if (documento instanceof VentasFactura || documento instanceof VentasNotaDeDebito) {
				debe = documento.getImportetotal().doubleValue();
				haber = 0.0;
				cell.setCellValue(debe);
			}
			cell = fila.getCell(4);
			if (documento instanceof VentasNotaDeCredito || documento instanceof VentasRecibo) {
				debe = 0.0;
				haber = documento.getImportetotal().doubleValue();
				cell.setCellValue(haber);
			}
			// SALDO
			cell = fila.getCell(5);
			saldo = saldo + debe - haber;
			cell.setCellValue(saldo);
			totalDebe = totalDebe + debe;
			totalHaber = totalHaber + haber;
			rownumber = rownumber + 1;
			fila = sheet.createRow(rownumber);
		}

		// FOOTER CENTER
		CellStyle tfooterCenterStyle = workbook.createCellStyle();
		tfooterCenterStyle.setFont(boldFont);
		tfooterCenterStyle.setBorderBottom(BorderStyle.MEDIUM);
		tfooterCenterStyle.setBorderTop(BorderStyle.MEDIUM);
		tfooterCenterStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
		tfooterCenterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		tfooterCenterStyle.setAlignment(HorizontalAlignment.CENTER);
		// FOOTER LEFT
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
		cell.setCellValue(totalDebe);
		theaderStyle.setDataFormat(format.getFormat("$###,###,###,###,##0.00"));
		cell.setCellStyle(theaderStyle);
		cell = filatotal.createCell(4);
		cell.setCellValue(totalHaber);
		cell.setCellStyle(theaderStyle);
		cell = filatotal.createCell(5);
		cell.setCellValue(saldo);
		cell.setCellStyle(theaderStyle);

		sheet.setColumnWidth(0, 12 * 256);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
	}

}
