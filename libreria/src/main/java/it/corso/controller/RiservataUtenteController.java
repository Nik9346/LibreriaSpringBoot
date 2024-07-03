package it.corso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.corso.model.Utente;
import it.corso.service.LibroOrdinatoService;
import it.corso.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/riservatautente")
public class RiservataUtenteController 
{
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private LibroOrdinatoService libroOrdinatoService;
	
	@GetMapping
	public String getPage(HttpSession session, Model model) {
		if(session.getAttribute("utente") == null)
			return "redirect:/loginutente";
		Utente utente = (Utente) session.getAttribute("utente"); //recuperiamo un object quindi dobbiamo castare a utente
		model.addAttribute("utente",utente);
		model.addAttribute("carrello", libroOrdinatoService.getCarrello(session));
		return "riservatautente";
	}
	
	@GetMapping("/logout")
	public String userLogout(HttpSession session) {
		session.removeAttribute("utente"); //rimuoviamo l'attributo utente dalla sessione
		return "redirect:/";
	}
	
	@PostMapping
	public String formManager(@Valid @ModelAttribute("utente") Utente utente, BindingResult result, HttpSession session) 
	{
		if(result.hasErrors())
			return "riservatautente";
		utenteService.registraUtente(utente);
		session.setAttribute("utente", utente);
		return "redirect:/riservatautente";
	}
}
