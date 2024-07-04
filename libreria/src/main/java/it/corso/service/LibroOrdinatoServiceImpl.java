package it.corso.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

	@Override
	public double getTotaleCarrello(HttpSession session) {
		Utente utente = (Utente) session.getAttribute("utente");
		// cerchiamo di ottenere il carrello
		@SuppressWarnings("unchecked")
		List<LibroOrdinato> carrello = (List<LibroOrdinato>) session.getAttribute("carrello_" + utente.getId()); // dobbiamo
																													// castare
																													// in
																													// downgrade
																													// e
																													// risolvere
																													// il
																													// warning
																													// con
																													// il
																													// suppress
		if (carrello != null)
			return carrello.stream().mapToDouble(lO -> lO.getLibro().getPrezzo() * lO.getQuantita()).reduce(0.0,
					(p1, p2) -> p1 + p2); // primo parametro dato di partenza, secondo parametro funzione lambda
											// dichiaro due variabili e poi le sommo

		return 0;
	}

	@Override
	public void rimozioneLibro(int idLibro, HttpSession session) {
		Utente utente = (Utente) session.getAttribute("utente");
		//otteniamo il carrello (siamo sicuri di averlo perchè clicchiamo su un link all'interno del carrello)
		@SuppressWarnings("unchecked")
		List<LibroOrdinato> carrello = (List<LibroOrdinato>) session.getAttribute("carrello_" + utente.getId());
		//rimuoviamo il libro dal carrello
		carrello = carrello.stream().filter(lO -> lO.getLibro().getId() != idLibro) // passano questo filtro tutti i libri tranne quello che ha id uguale a quello passato
				.toList();
		//capiamo se il carrello è vuoto o ha ancora elementi
		if(carrello.size()>0) //se il carrello ha ancora elementi
			session.setAttribute("carrello_" + utente.getId(), carrello);
		else {
			session.removeAttribute("carrello_"+ utente.getId()); //se il carrello è vuoto andiamo a rimuovere l'attributo dalla sessione
		}
	}

	@Override
	public String modificaQuantita(int idLibro, int quantitaRichiesta, HttpSession session) {
		Utente utente = (Utente) session.getAttribute("utente");
		@SuppressWarnings("unchecked")
		List<LibroOrdinato> carrello = (List<LibroOrdinato>) session.getAttribute("carrello_" + utente.getId());
		//acquisizione del libro dal database per controllo scorte
		Libro libro = libroService.getLibroById(idLibro);
		//acquisizione item carrello da modificare
		LibroOrdinato libroOrdinato = null;
		for(LibroOrdinato lO : carrello)
			if(lO.getLibro().getId() == idLibro) //se lO è uguale all'idLibro
			{
				libroOrdinato = lO; //imposto libro ordinato su quel libro e chiudo il ciclo
				break;
			}
		//valutazione ed eventuale modifica quantità
		if(quantitaRichiesta > libro.getQuantita() || quantitaRichiesta == 0) //se la quantità richiesta è superiore alla disponibilità o è uguale a 0 blocco la richiesta con no
			return "no";
		libroOrdinato.setQuantita(quantitaRichiesta);
		session.setAttribute("carrello_" +utente.getId(), carrello); //altrimenti salvo l'attributo in sessione
		DecimalFormat format = new DecimalFormat();
		format.setRoundingMode(RoundingMode.HALF_EVEN);
		return format.format(getTotaleCarrello(session));
		
	
	}

}
