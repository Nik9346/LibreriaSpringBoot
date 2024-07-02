package it.corso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.corso.model.Libro;
import it.corso.service.LibroService;


//localhost:8080/dettaglio
@Controller
@RequestMapping("/dettaglio")
public class DettaglioController {

	@Autowired
	private LibroService libroService;
	
	@GetMapping
	public String getPage(@RequestParam("id")int id, Model model) {
		Libro libro = libroService.getLibroById(id);
		model.addAttribute("libro",libro);
		return "dettaglio";
	}
	
}
