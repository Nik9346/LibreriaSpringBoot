package it.corso.model;





import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="libri_ordinati")
public class LibroOrdinato {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int quantita;
	
	@ManyToOne(cascade = CascadeType.REFRESH) //non andiamo mai a cancellare il libro stesso
	@JoinColumn(name = "id_libro", referencedColumnName = "id")
	private Libro libro;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name="id_ordine", referencedColumnName = "id")
	private Ordine ordine;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Ordine getOrdine() {
		return ordine;
	}

	public void setOrdine(Ordine ordine) {
		this.ordine = ordine;
	}

}
