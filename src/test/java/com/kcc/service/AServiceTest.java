package com.kcc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kcc.SpringBootActuatorDemoApplicationTests;

public class AServiceTest extends SpringBootActuatorDemoApplicationTests {

	@Autowired
	private AService aService;
	
	@Test
	public void testAService() throws InterruptedException {
		aService.goFuture();
		
		Thread.sleep(5000);
	}
}
