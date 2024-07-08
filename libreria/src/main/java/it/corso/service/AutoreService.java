package it.corso.service;

import java.util.List;

import it.corso.model.Autore;

public interface AutoreService 
{
	List<Autore> getAutori();
	Autore getAutoreById(int id);
	

}
