package it.corso.service;

import java.util.List;

import it.corso.model.Categoria;

public interface CategoriaService {
void registraCategoria(Categoria categoria);
List<Categoria> getCategorie();
Categoria getCategoriaById(int id);
void cancellaCategoria(int id);
}
