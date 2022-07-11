package com.recicladosplasticos.springboot.app.view.pdf;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.recicladosplasticos.springboot.app.models.entity.VentasFactura;
import com.recicladosplasticos.springboot.app.models.entity.ItemVentasFactura;

 


@Component("/ventas/factura/ver.pdf")
public class VentasFacturaPdfView extends AbstractPdfView {

	@Autowired
	MessageSource messageSource;
 

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		VentasFactura ventasFactura = (VentasFactura) model.get("factura");
		
		
 
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
		tablaCabecera.addCell("RECICLADOS PLASTICOS (LOGO)");
		tablaCabecera.addCell("FACTURA "); 
		tablaCabecera.addCell("     ");
		tablaCabecera.addCell(ventasFactura.getPrefijo() + " - " + dfNumero.format(ventasFactura.getNumero()));
		tablaCabecera.addCell("     ");
		tablaCabecera.addCell("FECHA: "+ ventasFactura.getFecha());
		tablaCabecera.setSpacingAfter(20);

		
		PdfPTable tablaEmpresa = new PdfPTable(1);
		tablaEmpresa.addCell("CUIT: XX-XXXXXXXX-X IVA: Responsable Inscripto");
		tablaEmpresa.addCell("DIRECCION: .........  CP:1900 LA PLATA Prov. Buenos Aires Argentina");
		tablaEmpresa.setSpacingAfter(10);
		
		 
		PdfPTable tablaCliente = new PdfPTable(1);
		
		PdfPCell celdaCliente = new PdfPCell(new Phrase("CLIENTE: ".concat(ventasFactura.getCliente().getNombre()) + " - RAZÓN SOCIAL: " + ventasFactura.getCliente().getRazonsocial()          ));
		celdaCliente.setBackgroundColor( new Color(39,146,169));
		celdaCliente.setPadding(5f);
		tablaCliente.addCell(celdaCliente);
		
	 	tablaCliente.addCell("CUIT: " + ventasFactura.getCliente().getCuit() + " - IVA: " + ventasFactura.getCliente().getIva());
		tablaCliente.addCell("DIRECCIÓN: " + ventasFactura.getCliente().getDireccion() + " CP ".concat(ventasFactura.getCliente().getCodigopostal())
							 + " Localidad " + ventasFactura.getCliente().getLocalidad());
		tablaCliente.addCell("Partido ".concat(ventasFactura.getCliente().getPartido()) + " Prov. ".concat(ventasFactura.getCliente().getProvincia())
				 			+ ", ".concat(ventasFactura.getCliente().getPais()));      
		tablaCliente.addCell("TEL: " + ventasFactura.getCliente().getTelefono() + " - EMAIL: " + ventasFactura.getCliente().getEmail());
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
		
		for(ItemVentasFactura item: ventasFactura.getItems()) {
			
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
		tablaProductos.addCell("$"+ dfCantidad.format(ventasFactura.getImporte()));
				
		PdfPCell iva = new 	PdfPCell(new Phrase("IVA:"));
		iva.setColspan(3);
		iva.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		iva.disableBorderSide(PdfPCell.TOP);
		iva.disableBorderSide(PdfPCell.BOTTOM);
		iva.disableBorderSide(PdfPCell.LEFT);
		
		tablaProductos.addCell(iva);
		tablaProductos.addCell("$"+ dfCantidad.format(ventasFactura.getImporteiva()));
				
		PdfPCell total = new 	PdfPCell(new Phrase("TOTAL:"));
		total.setColspan(3);
		total.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		total.disableBorderSide(PdfPCell.TOP);
		total.disableBorderSide(PdfPCell.BOTTOM);
		total.disableBorderSide(PdfPCell.LEFT);
		 
		
		tablaProductos.addCell(total);
		tablaProductos.addCell("$"+ dfCantidad.format(ventasFactura.getImportetotal()));
		
		document.add(tablaProductos);
		
		PdfPTable tablaObservaciones = new PdfPTable(1);
		tablaObservaciones.setSpacingBefore(30);
		tablaObservaciones.addCell("Observaciones");
		
		 
		if(ventasFactura.getObservacion() != null) {
			tablaObservaciones.addCell(ventasFactura.getObservacion().toString());
		} else {
			tablaObservaciones.addCell("");
		}
		document.add(tablaObservaciones);
		
	
	}
	 

 
}

	
	

 
