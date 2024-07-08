package it.corso.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.corso.model.Autore;
import it.corso.model.Categoria;
import it.corso.model.Libro;
import it.corso.service.AutoreService;
import it.corso.service.CategoriaService;
import it.corso.service.LibroService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//localhost:8080/adminlibri
@Controller
@RequestMapping("/adminlibri")
public class AdminLibriController {
	
	@Autowired
	private LibroService libroService;
	
	@Autowired
	private AutoreService autoreService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	//lo dichiariamo qui poichè diventa visibile e accessibile in tutti i metodi
	private Libro libro;
	
	@GetMapping()
	public String getPage(Model model) {
		List<Libro> libri = libroService.getLibri();
		List<Autore> autori = autoreService.getAutori();
		List<Categoria> catogorie = categoriaService.getCategorie();
		libro = new Libro(); //assume il valore in questa fase
		model.addAttribute("libri",libri);
		model.addAttribute("autori",autori);
		model.addAttribute("categorie",catogorie);
		return "admin-libri";
	}
	
	@PostMapping
	public String formManager(
			@RequestParam("titolo") String titolo, 
			@RequestParam("prezzo") String prezzo,
			@RequestParam("quantita") String quantita,
			@RequestParam("autore") int idAutore,
			@RequestParam("categoria") int idCategoria,
			@RequestParam(name="copertina", required = false) MultipartFile copertina) // assegniamo l'attributo con required false in quanto gestiamo la possibilità di non inserirlo
			{
		libroService.registraLibro(libro, titolo, prezzo, quantita, idAutore, idCategoria, copertina);
		return "redirect:/adminlibri";
	}
	

}
