package com.kcc.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class AService {

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private BService bService;

	public void goFuture() {
		CompletableFuture<Map<String, String>> future1 = getMapFuture("1");
		CompletableFuture<Map<String, String>> future2 = getMapFuture("2");
		CompletableFuture<Map<String, String>> future3 = getMapFuture("3");
		CompletableFuture<Map<String, String>> future4 = getMapFuture("4");
		CompletableFuture<Map<String, String>> future5 = getMapFuture("5");

		CompletableFuture<List<?>> allOf = allOf(future1, future2, future3, future4, future5);
		allOf.thenAccept(l -> l.forEach(m -> System.out.println(JSONObject.toJSONString(m))));
	}

	private CompletableFuture<List<?>> allOf(CompletableFuture<?>... cfs) {
		return CompletableFuture.allOf(cfs)
				.thenApply(ignore -> Stream.of(cfs).map(CompletableFuture::join).collect(Collectors.toList()));
	}

	private CompletableFuture<Map<String, String>> getMapFuture(String key) {
		return CompletableFuture.supplyAsync(() -> bService.getMap(key), threadPoolTaskExecutor);
	}

}
