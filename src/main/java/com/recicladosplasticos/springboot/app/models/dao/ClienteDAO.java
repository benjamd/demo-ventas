package com.recicladosplasticos.springboot.app.models.dao;


//import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Cliente;

public interface ClienteDAO  extends PagingAndSortingRepository<Cliente, Long> {
	

}
