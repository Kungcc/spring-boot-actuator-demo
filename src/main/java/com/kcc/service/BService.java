package com.kcc.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BService.class);

	private Map<String, TObject> tObjectMap = new HashMap<>();

	public Map<String, TObject> getTObjectMap(String id, String name) {
		LOGGER.info("id={}, startTime={}", id, LocalDateTime.now());
		TObject tObject = tObjectMap.getOrDefault(id, new TObject());
		if (tObject.getCount() == null) {
			tObject.setId(id);
			tObject.setName(name);
			tObject.setCount(1);
		} else {
			tObject.setCount(tObject.getCount() + 1);
		}
		tObjectMap.put(id, tObject);
		try {
			if (Integer.parseInt(id) % 2 == 1) {
				Thread.sleep(20000);
			} else {
				Thread.sleep(25000);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		LOGGER.info("id={}, endTime={}", id, LocalDateTime.now());
		return tObjectMap;
	}

}
