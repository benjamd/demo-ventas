package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.UsuarioDAO;
import com.recicladosplasticos.springboot.app.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		
		usuarioDao.save(usuario);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		usuarioDao.deleteById(id);
		
	}
	
	

}
