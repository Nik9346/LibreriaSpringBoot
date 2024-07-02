package it.corso.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // serve per farla diventare un'entità
@Table(name = "autori") // specifica il nome della tabella di riferimento
public class Autore {

	@Id // identifica che tipologia di chiave è
	@GeneratedValue(strategy = GenerationType.IDENTITY) // si utilizza IDENTITY in quanto della chiave primaria se ne
														// occupa l'autoincrement del database
	private int id;

	@Column // specifichiamo che rappresentano delle colonne se il nome è diverso tra
			// parentesi(name = "nome_autore") specifichiamo il nome della colonna di
			// riferimento
	private String nome;

	@Column
	private String cognome;

	/**
	 * orphanRemoval cancella tutti gli orfani rimasti quindi i libri collegati all'autore
	 */
	@OneToMany(mappedBy = "autore", // esatto nome dell'attributo che gestisce il vincolo
			fetch = FetchType.EAGER, // quando faccio una select su un autore recupero immediatamente id nome cognome
			orphanRemoval = true	// e carico tutti gli oggetti di tipo libro sfruttando la foreign key immediato
									// -- LAZY si utilizza poco in quanto complica la situazione, il caricamento è
									// più lento
			
	)
	private List<Libro> libri = new ArrayList<>();

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

	public List<Libro> getLibri() {
		return libri;
	}

	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}

}
