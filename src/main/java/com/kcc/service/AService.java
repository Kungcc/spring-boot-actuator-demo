package com.kcc.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		Map<String, TObject> result = new HashMap<>();
		List<String> tasks = Arrays.asList("1-First", "2-Second", "3-Third", "4-Forth", "5-Fifth");
		try {
			CompletableFuture<?>[] cfs = tasks.stream()
					// 呼叫web service拿物件
					.map(task -> getTObjectFuture(task)
							// 完成後放到map，r是物件，e是exception
							.whenComplete((r, e) -> result.put(r.getId(), r)))
					.toArray(CompletableFuture[]::new);

			CompletableFuture.allOf(cfs).get(30, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			LOGGER.error("ErrorMsg={}", e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		return result;
	}

	private CompletableFuture<TObject> getTObjectFuture(String task) {
		return CompletableFuture.supplyAsync(() -> bService.getTObject(task), threadPoolTaskExecutor)
				.exceptionally(ex -> {
					LOGGER.error("ErrorMsg={}", ex.getMessage(), ex);
					return null;
				});
	}
}
