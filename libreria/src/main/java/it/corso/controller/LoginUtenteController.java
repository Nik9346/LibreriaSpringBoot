package it.corso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.corso.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;


//localhost:8080/logiutente
@Controller
@RequestMapping("/loginutente")
public class LoginUtenteController {
	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public String getPage(HttpSession session) {
		if (session.getAttribute("utente") != null)
			return "redirect:/riservatautente"; // se l'utente ha effettuato l'accesso lo reindirizziamo alla sua pagina
		return "loginutente"; // altrimenti lo reindirizziamo di nuovo al loginutente
	}

	@PostMapping
	public String formManager(@RequestParam("username") String username, @RequestParam("password") String password,
			HttpSession session, RedirectAttributes redirectAttributes) {
		if (!utenteService.controlloLogin(username, password, session)) {
			redirectAttributes.addFlashAttribute("errore", "Credenziali errate");
			return "redirect:/loginutente";
		}

		return "redirect:/riservatautente";
	}

}
