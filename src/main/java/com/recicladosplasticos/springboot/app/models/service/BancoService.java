package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Banco;

public interface BancoService {
	
	public List<Banco> findAll();

	public Page<Banco> findAll(Pageable pageable);

	public void save(Banco banco);

	public Banco findOne(Long id);

	public void delete(Long id);


}
