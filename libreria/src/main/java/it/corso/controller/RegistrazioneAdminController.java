package it.corso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.corso.dao.AdminDao;
import it.corso.model.Admin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/provvisorio")
public class RegistrazioneAdminController {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/admin")
	@ResponseBody
	public String registraAdmin() {
		Admin admin = new Admin();
		admin.setUsername("admin");
		admin.setPassword(encoder.encode("admin")); //setto la password codificandola
		admin.setRole("Admin");
		adminDao.save(admin);
		return "";
		
	}
	
	@GetMapping("/employee")
	@ResponseBody
	public String registraDipendente() {
		Admin admin = new Admin();
		admin.setUsername("mario");
		admin.setPassword(encoder.encode("abc")); //setto la password codificandola
		admin.setRole("Employee");
		adminDao.save(admin);
		return "";
		
	}
}
