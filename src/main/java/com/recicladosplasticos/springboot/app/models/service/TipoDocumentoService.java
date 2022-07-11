package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import com.recicladosplasticos.springboot.app.models.entity.TipoDocumento;

public interface TipoDocumentoService  {

	public List<TipoDocumento> findAll();

	public void save(TipoDocumento tipodocumento);

	public TipoDocumento findOne(Long id);

	public void delete(Long id);
}
