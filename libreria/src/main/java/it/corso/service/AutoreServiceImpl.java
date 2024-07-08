package it.corso.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.corso.dao.AutoreDao;
import it.corso.model.Autore;

@Service
public class AutoreServiceImpl implements AutoreService {

	@Autowired
	AutoreDao autoreDao;
	
	@Override
	public List<Autore> getAutori() {
	return (List<Autore>) autoreDao.findAll();	
	}

	@Override
	public Autore getAutoreById(int id) {
		return autoreDao.findById(id).get();
	}
}
