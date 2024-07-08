package it.corso.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.corso.dao.AdminDao;
import it.corso.model.Admin;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	AdminDao adminDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //lo invoca il framework
		Optional<Admin> adminOptional = adminDao.findByUsername(username);
		
		//import org.springframework.security.core.userdetails.User;
		User admin = null;
		if(adminOptional.isEmpty())
			throw new UsernameNotFoundException("Autenticazione Fallita");
		Admin trovato = adminOptional.get(); //il metodo get tira fuori il contenuto dell'optional
		//impostiamo un set di Grantedauthority
		Set<GrantedAuthority> auth = new HashSet<>(); //hashset è la più performante anche se disordinata
		//impostiamo il ruolo dell'utente facendo un getRole su trovato
		auth.add(new SimpleGrantedAuthority(trovato.getRole()));
		
		//impostiamo l'admin creando un nuovo oggetto user e passandogli i dati trovati + l'auth
		admin = new User(trovato.getUsername(), trovato.getPassword(), auth);
		
		//ritorniamo l'admin
		return admin;
	}

}
