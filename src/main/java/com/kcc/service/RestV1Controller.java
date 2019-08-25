package com.kcc.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestV1Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestV1Controller.class);

	@Autowired
	private AService aService;

	@GetMapping("/demo")
	public Map<String, TObject> getMap() {
		long t1 = System.currentTimeMillis();
		Map<String, TObject> result = aService.goFuture();
		long t2 = System.currentTimeMillis();
		LOGGER.info("totalTime={}ms", (t2 - t1));
		return result;
	}
}
