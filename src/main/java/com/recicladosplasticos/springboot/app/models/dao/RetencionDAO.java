package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Retencion;

public interface RetencionDAO extends PagingAndSortingRepository<Retencion, Long>{

}
