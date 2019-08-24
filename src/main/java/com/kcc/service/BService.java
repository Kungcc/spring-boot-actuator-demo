package com.kcc.service;

import java.util.HashMap;
import java.util.Map;

public class BService {

	private Map<String, TObject> tObjectMap = new HashMap<>();

	public Map<String, TObject> getTObjectMap(String id, String name) {
		TObject tObject = tObjectMap.getOrDefault(id, new TObject());
		if (tObject.getCount() == null) {
			tObject.setId(id);
			tObject.setName(name);
			tObject.setCount(1);
		} else {
			tObject.setCount(tObject.getCount() + 1);
		}
		tObjectMap.put(id, tObject);
		return tObjectMap;
	}
}
