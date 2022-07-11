package com.recicladosplasticos.springboot.app.models.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
 

public interface Documento {
	
 
	public Long getId() ;

	public void setId(Long id); 

 	public String getObservacion(); 

	public void setObservacion(String observacion); 
	public Date getCreateAt()  ;

	public void setCreateAt(Date createAt); 

	public Cliente getCliente() ; 

	public void setCliente(Cliente cliente); 

	 
	public Double getSubTotal()  ;

	public Double totalMasIva()  ;

	public Double importeIva() ;


	public String getPrefijo() ; 

	public void setPrefijo(String prefijo) ;
	
	public Long getNumero()  ;

	public void setNumero(Long numero) ; 

	public Boolean getAnulada()  ;

	public void setAnulada(Boolean anulada) ;

	public BigDecimal getSaldopendiente()  ;

	public void setSaldopendiente(BigDecimal saldopendiente)  ;

	public BigDecimal getImporteiva()  ;

	public void setImporteiva(BigDecimal importeiva);  
	
	public BigDecimal getImporte() ;

	public void setImporte(BigDecimal importe); 

	public BigDecimal getImportetotal()  ;

	public void setImportetotal(BigDecimal importetotal);  
	public Date getFecha()  ;
	public void setFecha(Date fecha);  

	public String getLetra() ;
	public void setLetra(String letra); 

	public Double getAlicuotaiva() ;

	public void setAlicuotaiva(Double alicuotaiva);  

	public String getCondventa()  ;

	public void setCondventa(String condventa);  

	public String getDetalle()  ;

	public void setDetalle(String detalle)  ;
	
	public String getDocumento();

	public void setDocumento(String documento); 
	
	public List<Item> getLineas();
	
	public VentasPresupuesto getOrdenentrega();
	
	 
	
 
}
