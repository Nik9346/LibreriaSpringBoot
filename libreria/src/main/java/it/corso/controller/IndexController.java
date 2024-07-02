package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.corso.model.Libro;
import it.corso.service.LibroService;

//localhost:8080
@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private LibroService libroService;
	
	@GetMapping
	public String getPage(Model model) {
		List<Libro> libri = libroService.getLibri();
		model.addAttribute("libri",libri);
		
		return "index"; //identico al nome del file di view
	}
	
}
