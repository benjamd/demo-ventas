package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.ClienteCuentaCorriente;

public interface ClienteCuentaCorrienteDAO extends PagingAndSortingRepository<ClienteCuentaCorriente, Long> {

}
