package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.RoleDAO;
import com.recicladosplasticos.springboot.app.models.entity.Role;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO roleDao;

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		
		return (List<Role>) roleDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Role> findAll(Pageable pageable) {
		
		return roleDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Role role) {
		roleDao.save(role);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Role findOne(Long id) {
		
		return roleDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		roleDao.deleteById(id);
	}

	
	@Override
	@Transactional
	public List<Role> findAllByUserId(Long userId) {
		
		return roleDao.findAllByUserId(userId);
	}

	@Override
	@Transactional
	public Page<Role> findAllByUserId(Long userId, Pageable pageable) {
		
		return roleDao.findAllByUserId(userId, pageable);
	}

}
