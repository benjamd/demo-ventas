package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.recicladosplasticos.springboot.app.models.entity.TipoDocumento;

public interface TipoDocumentoDAO extends CrudRepository<TipoDocumento, Long> {

}
