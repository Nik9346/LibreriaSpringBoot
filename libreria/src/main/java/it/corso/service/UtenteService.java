package it.corso.service;

import it.corso.model.Utente;
import jakarta.servlet.http.HttpSession;

public interface UtenteService 
{
	void registraUtente(Utente utente);
	boolean controlloUsername(String username);
	boolean controlloLogin(String username, String password, HttpSession session); //passiamo username password e sessione di navigazione
}
