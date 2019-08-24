package com.kcc.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestV1Controller {
	
	@Autowired
	private AService aService;

	@GetMapping("/demo")
	public Map<String, TObject> getMap() {
		return aService.goFuture();
	}
}
