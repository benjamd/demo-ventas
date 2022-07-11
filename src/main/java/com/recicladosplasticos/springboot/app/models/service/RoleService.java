package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recicladosplasticos.springboot.app.models.entity.Role;

public interface RoleService {
	
	public List<Role> findAll();
	
	public Page<Role> findAll(Pageable pageable);
	
	public void save(Role role);
	
	public Role findOne(Long id);
	
	public void delete(Long id);
	
	public List<Role> findAllByUserId(Long userId);
	
	public Page<Role> findAllByUserId(Long userId,Pageable pageable);


}
