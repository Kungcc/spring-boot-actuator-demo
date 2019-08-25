package com.kcc.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
		List<String> requests = Arrays.asList("1-First", "2-Second", "3-Third", "4-Forth", "5-Fifth");
		try {
			CompletableFuture<?>[] cfs = requests.stream()
					// 呼叫web service拿物件
					.map(request -> getTObjectFuture(request)
							// 完成後放到map，r是物件，e是exception
							.whenComplete((r, e) -> result.put(r.getId(), r)))
					.toArray(CompletableFuture[]::new);

			CompletableFuture.allOf(cfs).get();
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("ErrorMsg={}", e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		return result;
	}

	private CompletableFuture<TObject> getTObjectFuture(String request) {
		return CompletableFuture.supplyAsync(() -> bService.getTObject(request), threadPoolTaskExecutor)
				.exceptionally(ex -> {
					throw new DemoServiceException(ex.getMessage());
				});
	}
}
