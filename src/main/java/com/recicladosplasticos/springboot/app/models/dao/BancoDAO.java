package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Banco;

public interface BancoDAO extends PagingAndSortingRepository<Banco, Long> {

}
