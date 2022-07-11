package com.recicladosplasticos.springboot.app.models.service;

import java.util.List;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recicladosplasticos.springboot.app.models.dao.ProductoDao;
import com.recicladosplasticos.springboot.app.models.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService{

	@Autowired
	private ProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		 
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		 
		return productoDao.findAll(pageable);
	}
	
	
	@Override
	@Transactional
	public void save(Producto producto) {
		 productoDao.save(producto);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findOne(Long id) {
		
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	  
	}

	@Override
	public Page<Producto> findByNombreAndDescripcion(String nombre, String descripcion, Pageable pageable) {
		 
		return productoDao.findByNombreLikeIgnoreCase(nombre, descripcion, pageable);
	}


}
