package com.kcc.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BService {

	public Map<String, String> getMap(String key) {
		Map<String, String> map = new HashMap<>();
		map.put(LocalDateTime.now().toString(), Long.toString(System.currentTimeMillis()));
		map.put(key, key);
		return map;
	}
}
