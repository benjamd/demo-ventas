package com.recicladosplasticos.springboot.app.view.pdf;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasNotaDeDebito;
import com.recicladosplasticos.springboot.app.models.entity.VentasNotaDeDebito;

@Component("/ventas/debito/ver")
public class VentasNotaDeDebitoPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		VentasNotaDeDebito debito = (VentasNotaDeDebito) model.get("debito");
		
		PdfPTable tablaLetra = new  PdfPTable(1);
		tablaLetra.setTotalWidth(new float[] {2.5f});
		 
		PdfPCell letra = new PdfPCell(new Phrase("A"));
		letra.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		letra.disableBorderSide(PdfPCell.TOP);
		letra.disableBorderSide(PdfPCell.BOTTOM);
		letra.disableBorderSide(PdfPCell.LEFT);
		letra.disableBorderSide(PdfPCell.RIGHT);
		tablaLetra.setWidths(new float[] {1.1f});
		tablaLetra.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
		tablaLetra.addCell(letra);;
		document.add(tablaLetra);
		
		DecimalFormat dfNumero = new DecimalFormat("00000000");
				
		PdfPTable tablaCabecera = new PdfPTable(2);
		tablaCabecera.setSpacingBefore(20);
		tablaCabecera.addCell("RECICLADOS LA PLATA (LOGO)");
		tablaCabecera.addCell("NOTA DE DEBITO "); 
		tablaCabecera.addCell("     ");
		tablaCabecera.addCell(debito.getPrefijo() + " - " + dfNumero.format(debito.getNumero()));
		tablaCabecera.addCell("     ");
		tablaCabecera.addCell("FECHA: "+ debito.getFecha());
		tablaCabecera.setSpacingAfter(20);

		
		PdfPTable tablaEmpresa = new PdfPTable(1);
		tablaEmpresa.addCell("CUIT: XX-XXXXXXXX-X IVA: Responsable Inscripto");
		tablaEmpresa.addCell("DIRECCION: .........  CP:1900 LA PLATA Prov. Buenos Aires Argentina");
		tablaEmpresa.setSpacingAfter(10);
		
		 
		PdfPTable tablaCliente = new PdfPTable(1);
		
		PdfPCell celdaCliente = new PdfPCell(new Phrase("CLIENTE: ".concat(debito.getCliente().getNombre()) + " - RAZÓN SOCIAL: " + debito.getCliente().getRazonsocial()          ));
		celdaCliente.setBackgroundColor( new Color(39,146,169));
		celdaCliente.setPadding(5f);
		tablaCliente.addCell(celdaCliente);
		
	 	tablaCliente.addCell("CUIT: " + debito.getCliente().getCuit() + " - IVA: " + debito.getCliente().getIva());
		tablaCliente.addCell("DIRECCIÓN: " + debito.getCliente().getDireccion() + " CP ".concat(debito.getCliente().getCodigopostal())
							 + " Localidad " + debito.getCliente().getLocalidad());
		tablaCliente.addCell("Partido ".concat(debito.getCliente().getPartido()) + " Prov. ".concat(debito.getCliente().getProvincia())
				 			+ ", ".concat(debito.getCliente().getPais()));      
		tablaCliente.addCell("TEL: " + debito.getCliente().getTelefono() + " - EMAIL: " + debito.getCliente().getEmail());
		tablaCliente.setSpacingAfter(20);
		
		document.add(tablaCabecera);
		document.add(tablaEmpresa);
		document.add(tablaCliente);
		
		PdfPTable tablaDescripcion = new  PdfPTable(1);
		PdfPCell descripcion = new 	PdfPCell(new Phrase("DESCRIPCION"));
		//descripcion.setColspan(1);
		descripcion.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		descripcion.setPadding(5f);
		descripcion.setBackgroundColor( new Color(39,146,169));
		tablaDescripcion.addCell(descripcion);
		document.add(tablaDescripcion);
		
		PdfPTable tablaProductos = new  PdfPTable(4);
		tablaProductos.setWidths(new float[] {1,2.5f,1,1});
		tablaProductos.addCell("CANT. Kg.");
		tablaProductos.addCell("PRODUCTO");
		tablaProductos.addCell("PRECIO");
		tablaProductos.addCell("IMPORTE");
		
		DecimalFormat dfCantidad = new DecimalFormat("###,###,###,##0.###");
		DecimalFormat dfImporte = new DecimalFormat("###,###,###,##0.00");
		
		for(ItemVentasNotaDeDebito item: debito.getItems()) {
			
			tablaProductos.addCell(dfCantidad.format(item.getCantidad()));
			tablaProductos.addCell(item.getProducto().getNombre());
			tablaProductos.addCell("$"+ dfImporte.format(item.getPrecio()));
			tablaProductos.addCell("$"+ dfImporte.format(item.calcularImporte()));
		}
		
	
		
		PdfPCell subtotal = new 	PdfPCell(new Phrase("SUBTOTAL:"));
		subtotal.setColspan(3);
		subtotal.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		subtotal.disableBorderSide(PdfPCell.BOTTOM);
		subtotal.disableBorderSide(PdfPCell.LEFT);
		
		tablaProductos.addCell(subtotal);
		tablaProductos.addCell("$"+ dfCantidad.format(debito.getImporte()));
				
		PdfPCell iva = new 	PdfPCell(new Phrase("IVA:"));
		iva.setColspan(3);
		iva.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		iva.disableBorderSide(PdfPCell.TOP);
		iva.disableBorderSide(PdfPCell.BOTTOM);
		iva.disableBorderSide(PdfPCell.LEFT);
		
		tablaProductos.addCell(iva);
		tablaProductos.addCell("$"+ dfCantidad.format(debito.getImporteiva()));
				
		PdfPCell total = new 	PdfPCell(new Phrase("TOTAL:"));
		total.setColspan(3);
		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		total.disableBorderSide(PdfPCell.TOP);
		total.disableBorderSide(PdfPCell.BOTTOM);
		total.disableBorderSide(PdfPCell.LEFT);
		 
		
		tablaProductos.addCell(total);
		tablaProductos.addCell("$"+ dfCantidad.format(debito.getImportetotal()));
		
		document.add(tablaProductos);
		
		PdfPTable tablaObservaciones = new PdfPTable(1);
		tablaObservaciones.setSpacingBefore(30);
		tablaObservaciones.addCell("Observaciones");
		
		 
		if(debito.getObservacion() != null) {
			tablaObservaciones.addCell(debito.getObservacion().toString());
		} else {
			tablaObservaciones.addCell("");
		}
		document.add(tablaObservaciones);
		
		
	}
	

}
