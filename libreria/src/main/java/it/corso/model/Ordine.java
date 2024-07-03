package it.corso.model;

import java.time.LocalDate;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordini")
public class Ordine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column // sparisce anche la difficoltà nell'integrazione della data per il problema del
			// localdate
	private LocalDate data;
	@Column
	private double importo;

	@ManyToOne(cascade = CascadeType.REFRESH) // lato di partenza della foreign key // refresh in quanto andiamo ad aggiornare i dati, nel momento in cui eliminiamo l'ordine non tocchiamo anagrafica cliente
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private Utente utente;
	
	@OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true) // tutto quello che riguarda un ordine riguarda anche la conseguente lista di libri ordinati
	private List<LibroOrdinato> libriordinati = new ArrayList<>();

	public List<LibroOrdinato> getLibriordinati() {
		return libriordinati;
	}

	public void setLibriordinati(List<LibroOrdinato> libriordinati) {
		this.libriordinati = libriordinati;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getImporto() {
		return importo;
	}

	public void setImporto(double importo) {
		this.importo = importo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}
