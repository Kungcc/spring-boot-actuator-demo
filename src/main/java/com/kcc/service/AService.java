package com.kcc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class AService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AService.class);

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private BService bService;

	public Map<String, TObject> goFuture() {
		CompletableFuture<Map<String, TObject>> future1 = getTObjectMapFuture("1", "First");
		CompletableFuture<Map<String, TObject>> future2 = getTObjectMapFuture("2", "Second");
		CompletableFuture<Map<String, TObject>> future3 = getTObjectMapFuture("3", "Third");
		CompletableFuture<Map<String, TObject>> future4 = getTObjectMapFuture("4", "Forth");
		CompletableFuture<Map<String, TObject>> future5 = getTObjectMapFuture("5", "Fifth");

		CompletableFuture<List<?>> allOf = allOf(future1, future2, future3, future4, future5);

		Map<String, TObject> result = new HashMap<>();
		allOf.thenAccept(l -> l.forEach(m -> {
			@SuppressWarnings("unchecked")
			Map<String, TObject> temp = (Map<String, TObject>) m;
			result.putAll(temp);
			LOGGER.info(JSONObject.toJSONString(m));
		}));
		
		return result;
	}

	private CompletableFuture<List<?>> allOf(CompletableFuture<?>... cfs) {
		return CompletableFuture.allOf(cfs)
				.thenApply(ignore -> Stream.of(cfs).map(CompletableFuture::join).collect(Collectors.toList()));
	}

	private CompletableFuture<Map<String, TObject>> getTObjectMapFuture(String id, String name) {
		return CompletableFuture.supplyAsync(() -> bService.getTObjectMap(id, name), threadPoolTaskExecutor)
				.exceptionally(ex -> {
					LOGGER.error("ErrorMsg={}", ex.getMessage(), ex);
					return null;
				});
	}

}
