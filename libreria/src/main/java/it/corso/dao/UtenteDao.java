package it.corso.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.corso.model.Utente;

public interface UtenteDao extends CrudRepository<Utente, Integer> {
	Utente findByProfiloUsername(String username); // select generata da noi seguendo la sintassi jakarta select from
													// profili where username = username, si scrive profilo username in
													// quanto in classe utenti c'è profilo, sotto classe profilo
													// username

	@Query(value = "SELECT * FROM utenti JOIN profili ON utenti.id_profilo = profili.id WHERE " // in questo caso
																								// passiamo un query che
																								// scriviamo
																								// personalmente
			+ "profili.username=:u", // :u è un segnaposto per quello che gli dobbiamo passare
			nativeQuery = true) // stiamo dicendo che la query è in linguaggio nativo

	Utente controlloUsername(@Param("u") String username); // specifichiamo con @Param il carattere di riferimento
	
	Utente findByProfiloUsernameAndProfiloPassword(String username, String password); // generiamo questa query e passiamo username e password recuperati da un loginForm, Fondamentale il CamelCase e il rispetto assoluto dei nomi

}
