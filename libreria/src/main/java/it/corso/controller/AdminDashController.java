package it.corso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


//localhost:8080/admindash
@Controller
@RequestMapping("/admindash")
public class AdminDashController {

	@GetMapping
	public String getPage() {
		return "admin-dash";
	}
	
}
