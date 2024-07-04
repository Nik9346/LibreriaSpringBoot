package it.corso.service;

import java.util.List;

import it.corso.model.LibroOrdinato;
import jakarta.servlet.http.HttpSession;

public interface LibroOrdinatoService {
	boolean aggiuntaLibro(int idLibro, HttpSession session); //metodo per aggiungere libro al carrello
	List<LibroOrdinato> getCarrello(HttpSession session); //metodo per ottenere il carrello
	double getTotaleCarrello(HttpSession session); //la gestione del carrello viene implementata nella sessione
	void rimozioneLibro(int idLibro, HttpSession session); //metodo per la rimozione del libro dal carrello
	String modificaQuantita(int idLibro, int quantitaRichiesta, HttpSession session);
	
	

}
