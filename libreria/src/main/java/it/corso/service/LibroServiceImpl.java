package it.corso.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.corso.dao.LibroDao;
import it.corso.model.Libro;

@Service //identifica una determinata classe rendendola un componente strutturale DA RICORDARE SEMPRE
public class LibroServiceImpl implements LibroService {

	@Autowired // svolge lo stesso compito di EJB va a recuperare il componente dal container
	private LibroDao libroDao;
	
	@Autowired
	private AutoreService autoreService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Override
	public List<Libro> getLibri() {
		
		return (List<Libro>) libroDao.findAll(); //ci arriviamo tramite ereditarietà
	}

	@Override
	public Libro getLibroById(int id) {
		Optional<Libro> libroOptional = libroDao.findById(id);
		if(libroOptional.isPresent())
			return libroOptional.get();
		return null;
	}

	@Override
	public void registraLibro(Libro libro, String titolo, String prezzo, String quantita, int idAutore, int idCategoria,
			MultipartFile copertina) {
		libro.setTitolo(titolo);
		libro.setPrezzo(Double.parseDouble(prezzo)); //meglio fare sempre un trycatch
		libro.setQuantita(Integer.parseInt(quantita));
		libro.setAutore(autoreService.getAutoreById(idAutore));
		libro.setCategoria(categoriaService.getCategoriaById(idCategoria));
		
		//una volta costruito tutto l'oggetto libro lo salviamo tramite la dao
		libroDao.save(libro);
		
	}
}
