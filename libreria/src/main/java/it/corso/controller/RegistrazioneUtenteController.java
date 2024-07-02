package it.corso.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.corso.model.Utente;
import it.corso.service.UtenteService;
import jakarta.validation.Valid;

//localhost:8080/registrazioneutente
@Controller
@RequestMapping("/registrazioneutente")
public class RegistrazioneUtenteController {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public String getPage(Model model) 
	{
		Utente utente = new Utente();
		model.addAttribute("utente",utente);
		return "registrazioneutente";
	}
	
	@PostMapping
	public String formManager(@Valid @ModelAttribute("utente") Utente utente, 
								BindingResult result, 
								Model model,
								RedirectAttributes redirectAttributes) { //il valid serve per verificare che l'attributo utente recuperato sia validitato, il bindingresult è un registro di errori
		if(result.hasErrors())
			return "registrazioneutente";
		//controllo duplicato per username
		if(!utenteService.controlloUsername(utente.getProfilo().getUsername())) // gli passiamo il valore che è stato inserito alla costruzione dell'oggetto con il campo input
		{
			model.addAttribute("duplicato","Username non disponibile");
			return "registrazioneutente";
		}
		
		utenteService.registraUtente(utente);
		redirectAttributes.addFlashAttribute("registrato","Grazie per esserti registrato"); //l'attributo flash non viene visualizzato nei requestparam
		return "redirect:/"; //si deve mettere sempre redirect:/pathdovesidesideranadare
	}
}
