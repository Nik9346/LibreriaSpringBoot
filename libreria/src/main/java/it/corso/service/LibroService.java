package it.corso.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.corso.model.Libro;

public interface LibroService {
	List<Libro> getLibri();
	
	Libro getLibroById(int id);
	
	void registraLibro(Libro libro, String titolo, String prezzo, String quantita, int idAutore, int idCategoria, MultipartFile copertina);
}
