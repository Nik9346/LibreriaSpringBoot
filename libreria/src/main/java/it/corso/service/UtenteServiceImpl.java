package it.corso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.UtenteDao;
import it.corso.model.Utente;
import jakarta.servlet.http.HttpSession;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteDao utenteDao;
	
	
	@Override
	public void registraUtente(Utente utente) 
	{
		utenteDao.save(utente);
	}


	@Override
	public boolean controlloUsername(String username) {
		if(utenteDao.findByProfiloUsername(username) == null) //se nessuno si è già registrato torniamo true altrimenti false
			return true;
		return false;
	}


	@Override
	public boolean controlloLogin(String username, String password, HttpSession session) {
		Utente utente = utenteDao.findByProfiloUsernameAndProfiloPassword(username, password);
		if (utente != null) {
			session.setAttribute("utente", utente); // salviamo sulla sessione di navigazione l'utente trovato
			return true;
		}
		return false;
	}

}
