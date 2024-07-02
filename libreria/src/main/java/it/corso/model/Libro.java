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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libri")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String titolo;
	
	@Column
	private String copertina;
	
	@Column
	private double prezzo;
	
	@ManyToOne(cascade = CascadeType.REFRESH) // la scelta giusta è questa REFRESH in quanto se modifico un libro non devo modificare l'autore
	@JoinColumn(name="id_autore",referencedColumnName = "id")
	private Autore autore;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_categoria", referencedColumnName = "id")
	private Categoria categoria;
	
	@ManyToMany(fetch = FetchType.EAGER) // carichiamo subito gli ordini nei quali il libro è incluso
	@JoinTable
	(
			name = "ordini_libri",//nome esatto della tabella di join
			joinColumns = @JoinColumn(name = "id_libro", referencedColumnName = "id"), //primo lato della tabella di join
			inverseJoinColumns = @JoinColumn(name="id_ordine", referencedColumnName = "id")  //altro lato della tabella di join
			)
	private List<Ordine> ordini = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCopertina() {
		return copertina;
	}

	public void setCopertina(String copertina) {
		this.copertina = copertina;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public Autore getAutore() {
		return autore;
	}

	public void setAutore(Autore autore) {
		this.autore = autore;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}
}
