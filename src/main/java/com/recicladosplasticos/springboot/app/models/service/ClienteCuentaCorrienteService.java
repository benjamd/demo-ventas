package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.ClienteCuentaCorriente;

public interface ClienteCuentaCorrienteService {

	
	public List<ClienteCuentaCorriente> findAll();

	public Page<ClienteCuentaCorriente> findAll(Pageable pageable);

	public void save(ClienteCuentaCorriente clienteCuentaCorriente);

	public ClienteCuentaCorriente findOne(Long id);

	public void delete(Long id);
}
