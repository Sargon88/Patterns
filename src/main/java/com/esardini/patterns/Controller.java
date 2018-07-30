package com.esardini.patterns;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@RequestMapping("/")
	public String index() {
		System.out.println("INDEX!");
		return "Ciao 2!!!";
	}

}
