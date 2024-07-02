package it.corso.dao;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Libro;

public interface LibroDao extends CrudRepository<Libro, Integer>{ // estende crud al quale va passato classe Libro e l'id che è Integer

}
