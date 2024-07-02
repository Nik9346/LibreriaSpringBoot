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


@Entity
@Table(name = "categorie")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // dobbiamo ricordarci che l'id deve essere sempre uguale al corrispettivo campo id della tabella, non c'è un modo per cambiare il nome di riferimento
	
	@Column
	private String descrizione;
	
	
	@OneToMany(
			mappedBy = "categoria", //mappata dall'attributo categoria in classe Libri
			fetch = FetchType.EAGER,
			orphanRemoval = true // rimuovo i libri che appartengono alla categoria rimossa
			)
	
	private List<Libro> libri = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	public List<Libro> getLibri() {
		return libri;
	}

	public void setLibri(List<Libro> libri) {
		this.libri = libri;
	}

}
