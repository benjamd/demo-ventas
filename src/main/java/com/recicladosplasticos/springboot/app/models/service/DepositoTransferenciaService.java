package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.DepositoTransferencia;

public interface DepositoTransferenciaService {
	
	public List<DepositoTransferencia> findAll();

	public Page<DepositoTransferencia> findAll(Pageable pageable);

	public void save(DepositoTransferencia depotransfer);

	public DepositoTransferencia findOne(Long id);

	public void delete(Long id);


}
