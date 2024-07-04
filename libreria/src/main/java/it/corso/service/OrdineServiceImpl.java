package it.corso.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.OrdineDao;
import it.corso.model.LibroOrdinato;
import it.corso.model.Ordine;
import it.corso.model.Utente;
import jakarta.servlet.http.HttpSession;


@Service
public class OrdineServiceImpl implements OrdineService {

	@Autowired
	private OrdineDao ordineDao;
	
	@Autowired
	private LibroOrdinatoService libroOrdinatoService;
	
	@Override
	public void inviaOrdine(HttpSession session) {
		
		//otteniamo l'utente loggato e il contenuto del carrello
		Utente utente = (Utente) session.getAttribute("utente");
		List<LibroOrdinato> carrello = libroOrdinatoService.getCarrello(session);
		//creiamo l'oggetto ordine
		Ordine ordine = new Ordine();
		//impostiamo la data ordine (del momento in cui viene invocata la logica del metodo)
		ordine.setData(LocalDate.now());
		//impostiamo l'importo dell'ordine (totale carrello)
		ordine.setImporto(libroOrdinatoService.getTotaleCarrello(session));
		//impostiamo utente ordine
		ordine.setUtente(utente);
		//impostiamo lista libri ordinati dell'ordine
		ordine.setLibriordinati(carrello);
		//associamo ad ogni elemento del carrello l'ordine
		carrello.forEach(lO -> lO.setOrdine(ordine));
		//passiamo l'ordine al DAO per il salvataggio
		ordineDao.save(ordine);
		//eliminiamo il carrello dalla session
		session.removeAttribute("carrello_"+utente.getId());
	}

}
