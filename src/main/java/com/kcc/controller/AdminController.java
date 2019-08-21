package com.kcc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@GetMapping("/admin/mem")
	public List<String> mem() {
		long max = Runtime.getRuntime().maxMemory() / 1000000;
		long total = Runtime.getRuntime().totalMemory() / 1000000;
		long free = Runtime.getRuntime().freeMemory() / 1000000;
		long used = total - free;

		List<String> lines = new ArrayList<>();
		lines.add("Memory: ");
		lines.add("max: " + max + "MB");
		lines.add("total: " + total + "MB");
		lines.add("free: " + free + "MB");
		lines.add("used: " + used + "MB");
		return lines;
	}

}
