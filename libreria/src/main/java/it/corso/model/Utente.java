package it.corso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "utenti")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	@Pattern(regexp = "[a-zA-Z\\sàèìòù']{1,50}", message = "Caratteri non ammessi")
	private String nome;
	
	
	@Column
	@Pattern(regexp = "[a-zA-Z\\sàèìòù']{1,50}", message = "Caratteri non ammessi")
	private String cognome;
	
	@OneToOne(cascade = CascadeType.ALL) //ad ogni modifica dell'utente il profilo collegato si aggiorna di conseguenza
	@JoinColumn(name = "id_profilo", referencedColumnName = "id") //tabella di partenza join - arrivo join
	@Valid
	private Profilo profilo;
	
	@OneToMany // ad un utente corrispondo molti ordini lato di arrivo della foreign key
	(
			mappedBy = "utente", //viene mappata dalla variabile utente presente nell'entità ordine
			fetch = FetchType.EAGER, // attributi di collezionamento, eager tutti i dati vengono caricati subito, lazy recupero solo utenti non ordini
			orphanRemoval = true // tutti gli ordini relativi all'utente vengono cancellati
	)
	private List<Ordine> ordini = new ArrayList<>();
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public Profilo getProfilo() {
		return profilo;
	}


	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}


	public List<Ordine> getOrdini() {
		return ordini;
	}


	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}


}
