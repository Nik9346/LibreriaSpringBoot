package it.corso.dao;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Admin;



public interface AdminDao extends CrudRepository<Admin, Integer>
{
	Optional<Admin>findByUsername(String username);
}
