package com.recicladosplasticos.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Cheque;

public interface ChequeDAO  extends PagingAndSortingRepository<Cheque, Long>{

}
