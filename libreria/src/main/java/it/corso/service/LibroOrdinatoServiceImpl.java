package it.corso.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.model.Libro;
import it.corso.model.LibroOrdinato;
import it.corso.model.Utente;
import jakarta.servlet.http.HttpSession;

@Service
public class LibroOrdinatoServiceImpl implements LibroOrdinatoService {

	@Autowired
	private LibroService libroService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean aggiuntaLibro(int idLibro, HttpSession session) {
		// recuperiamo il libro
		Libro libro = libroService.getLibroById(idLibro); // recuperiamo il libro passando al service l'id del libro
		// recuperiamo l'utente loggato
		Utente utente = (Utente) session.getAttribute("utente"); // il ritorno è un object quindi va fatto downcast a
																	// Utente
		// capiamo se l'utente loggato ha un carrello oppure no
		List<LibroOrdinato> carrello;
		if (session.getAttribute("carrello_" + utente.getId()) != null) // ha il carrello
		{
			carrello = (List<LibroOrdinato>) session.getAttribute("carrello_" + utente.getId()); // recuperiamo il
																									// carrello presente
																									// nella session
			// capiamo se quel libro è già presente nel carrello
			for (LibroOrdinato lo : carrello) {
				if (lo.getLibro().getId() == idLibro)
					return false;
			}
			// creiamo l'oggetto libro ordinato e ne settiamo quanto più possibile
			LibroOrdinato libroOrdinato = new LibroOrdinato();
			libroOrdinato.setQuantita(1);
			libroOrdinato.setLibro(libro);
			carrello.add(libroOrdinato);
			// sovrascriviamo l'attributo di sessione del carrello con i nuovi valori
			session.setAttribute("carrello_" + utente.getId(), carrello);
			return true;

		} else { // non ha un carrello
			carrello = new ArrayList<>();
			// creiamo l'oggetto libro ordinato e ne settiamo quanto più possibile
			LibroOrdinato libroOrdinato = new LibroOrdinato();
			libroOrdinato.setQuantita(1);
			libroOrdinato.setLibro(libro);
			carrello.add(libroOrdinato);
			// salviamo il carrello come attributo di sessione
			session.setAttribute("carrello_" + utente.getId(), carrello);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LibroOrdinato> getCarrello(HttpSession session) {
		Utente utente = (Utente) session.getAttribute("utente");
		if (session.getAttribute("carrello_" + utente.getId()) != null)
			return (List<LibroOrdinato>) session.getAttribute("carrello_" + utente.getId());
		return null;
	}

}
