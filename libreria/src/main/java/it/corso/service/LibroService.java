package it.corso.service;

import java.util.List;

import it.corso.model.Libro;

public interface LibroService {
	List<Libro> getLibri();
	
	Libro getLibroById(int id);
}
