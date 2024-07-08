package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.corso.model.Categoria;
import it.corso.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



//localhost:8080/categorie
@Controller
@RequestMapping("/admincategorie")
public class AdminCategorieController {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public String getPage(Model model, @RequestParam(name = "id", required = false) Integer id) // lo associamo ad un
																								// Integer perchè è
																								// l'oggetto e ci
																								// permette quando
																								// andiamo a istanziare
																								// la variabile
																								// categoria, di
																								// valorizzarla con un
																								// ternario
	{
		List<Categoria> categorie = categoriaService.getCategorie();
		Categoria categoria = id == null ? new Categoria() : categoriaService.getCategoriaById(id);
		model.addAttribute("categorie", categorie);
		model.addAttribute("categoria", categoria);
		return "admin-categorie";
	}

	@PostMapping
	public String formManager(@ModelAttribute("categoria") Categoria categoria) { // recuperiamo l'oggetto passato al
																					// model come categoria

		categoriaService.registraCategoria(categoria); // passiamo al metodo save la categoria e registriamo il record
		return "redirect:/admincategorie";
	}
	
		@GetMapping("/elimina")
		public String eliminaCategoria(@RequestParam("id") int id) {
			categoriaService.cancellaCategoria(id);
			return "redirect:/admincategorie";
		}
		
	

}
