package it.corso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.corso.model.Libro;
import it.corso.service.LibroOrdinatoService;
import it.corso.service.LibroService;
import jakarta.servlet.http.HttpSession;

//localhost:8080/dettaglio
@Controller
@RequestMapping("/dettaglio")
public class DettaglioController {

	@Autowired
	private LibroService libroService;
	
	@Autowired
	private LibroOrdinatoService libroOrdinatoService;

	@GetMapping
	public String getPage(@RequestParam("id") int id, Model model, HttpSession session, @RequestParam(name="risultato", required = false) String risultato) {
		Libro libro = libroService.getLibroById(id);
		model.addAttribute("libro", libro);
		model.addAttribute("isLog", session.getAttribute("utente") != null);
		model.addAttribute("risultato",risultato);
		return "dettaglio";
	}

	@GetMapping("/compra")
	public String compraLibro(@RequestParam("id") int idLibro, HttpSession session,
			RedirectAttributes redirectAttributes) 
	{
		String risultato = libroOrdinatoService.aggiuntaLibro(idLibro, session) ? "ok" : "no";
		redirectAttributes.addAttribute("id", idLibro); // gli passiamo l'idLibro come attributo con il redirectAttributes
		redirectAttributes.addAttribute("risultato",risultato); //gli passiamo come attributo il risultato
		return "redirect:/dettaglio";
	}

}
