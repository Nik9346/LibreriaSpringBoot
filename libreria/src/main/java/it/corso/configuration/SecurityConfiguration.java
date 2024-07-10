package it.corso.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import it.corso.service.AdminService;

@EnableWebSecurity // abilitiamo il protocollo di sicurezza per l'ambito web
@Configuration // classe di configurazione
public class SecurityConfiguration {

	@Autowired
	private AdminService adminService;

	@Bean // ci permette di gestire il metodo in automatico dal framework
	BCryptPasswordEncoder getEncoder() { // metodo che ci permette di criptare le password
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(adminService); // gli passiamo il servizio che permette poi di dialogare con il
														// db
		provider.setPasswordEncoder(getEncoder()); // passiamo l'invocazione del metodo passwordEncoder
		return provider;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz // questa impostazione al momento permette di rendere tutto libero
													// per tutti, ci permette di sbloccare l'applicazione
				.requestMatchers("/css/**", "/javascript/**", "/image/**", "/", "/provvisorio/**", "/dettaglio/**",
						"/loginutente", "/registrazioneutente", "/riservatautente/**","/login") // tutte le risorse statiche sono
																					// accessibili a tutti, oltre alle
																					// pagine principali tra cui anche il login se è custom
				.permitAll()
				.requestMatchers("/admindash","/adminlibri/**").hasAnyAuthority("Admin","Employee") //aree disponibili ai ruoli admin e employee
				.requestMatchers("/admincategorie/**").hasAuthority("Admin") //aree disponibili ai ruoli di admin
				.anyRequest().authenticated()) //ogni richiesta che arriva deve essere autenticata secondo questo schema definito 
				
				.formLogin(login -> login.loginPage("/login") //pagina di login
										.loginProcessingUrl("/login") //reindirizzamento verso la pagina di login custom
										.failureUrl("/login?error") //quando c'è un fallimento delle credenziali passiamo in query string il parametro error
										.usernameParameter("username") //valore di username
										.passwordParameter("password") //valore di password
										.defaultSuccessUrl("/admindash", true)) //se il login ha successo mandiamo chi si è appena loggato in admindash il parametro true indica di utilizzare sempre quel path, perchè potrebbe differire
				
				.exceptionHandling(handling ->handling.accessDeniedPage("/login")) //il path se lo prende in automatico springSecurity
				
				.csrf(csrf -> csrf.disable()) //bypassa un blocco che si crea lavorando in locale tutti i form non funzionerebbero poichè servirebbe un token autorizzativo
				
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))) //creiamo un intercettore di richieste che ci permette di fare il logout
				
				// viene invocato lo strumento di autenticazione
				.authenticationProvider(getAuthenticationProvider());
		return http.build();

	}
}
