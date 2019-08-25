package com.kcc.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BService.class);

	public TObject getTObject(String task) {
		String id = task.split("-")[0];
		String name = task.split("-")[1];
		LOGGER.info("id={}, startTime={}", id, LocalDateTime.now());
		TObject tObject = new TObject();
		tObject.setId(id);
		tObject.setName(name);

		try {
			// 模擬長的執行時間
			Thread.sleep(20000 + (Long.parseLong(id) * 1000));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		LOGGER.info("id={}, endTime={}", id, LocalDateTime.now());
		return tObject;
	}
}
