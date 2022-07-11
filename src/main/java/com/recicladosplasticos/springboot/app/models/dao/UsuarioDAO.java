package com.recicladosplasticos.springboot.app.models.dao;

 
import org.springframework.data.repository.PagingAndSortingRepository;

import com.recicladosplasticos.springboot.app.models.entity.Usuario;

public interface UsuarioDAO extends PagingAndSortingRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
}
