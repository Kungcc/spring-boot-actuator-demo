package com.kcc.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AService.class);

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private BService bService;

	public Map<String, TObject> goFuture() {
		Map<String, CompletableFuture<Map<String, TObject>>> tObjectMap = new HashMap<>();
		tObjectMap.put("1", getTObjectMapFuture("1", "First"));
		tObjectMap.put("2", getTObjectMapFuture("2", "Second"));
		tObjectMap.put("3", getTObjectMapFuture("3", "Third"));
		tObjectMap.put("4", getTObjectMapFuture("4", "Forth"));
		tObjectMap.put("5", getTObjectMapFuture("5", "Fifth"));

		CompletableFuture<Map<String, TObject>> resultFuture = tObjectMap.get("1");
		for (Entry<String, CompletableFuture<Map<String, TObject>>> entry : tObjectMap.entrySet()) {
			if ("1".equals(entry.getKey())) {
				continue;
			}
			resultFuture.thenCombine(entry.getValue(), this::combineTObject);
		}

		Map<String, TObject> result = null;
		try {
			result = resultFuture.get(30, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			Thread.currentThread().interrupt();
		}
		return result;
	}

	private Map<String, TObject> combineTObject(Map<String, TObject> one, Map<String, TObject> two) {
		one.putAll(two);
		return one;
	}

	private CompletableFuture<Map<String, TObject>> getTObjectMapFuture(String id, String name) {
		return CompletableFuture.supplyAsync(() -> bService.getTObjectMap(id, name), threadPoolTaskExecutor)
				.exceptionally(ex -> {
					LOGGER.error("ErrorMsg={}", ex.getMessage(), ex);
					return null;
				});
	}

}
