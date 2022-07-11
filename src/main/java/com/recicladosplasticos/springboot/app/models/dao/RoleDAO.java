package com.recicladosplasticos.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Role;

public interface RoleDAO extends PagingAndSortingRepository<Role, Long> {
	
	@Query("SELECT r FROM Role r WHERE r.userId = ?1")
	public List<Role> findAllByUserId(Long userId);
	
	@Query(	"SELECT r FROM Role r WHERE r.userId = ?1")
	public Page<Role> findAllByUserId(Long userId,Pageable pageable);


}
