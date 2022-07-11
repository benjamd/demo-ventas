package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Cheque;

public interface ChequeService {
	
	public List<Cheque> findAll();

	public Page<Cheque> findAll(Pageable pageable);

	public void save(Cheque cheque);

	public Cheque findOne(Long id);

	public void delete(Long id);


}
