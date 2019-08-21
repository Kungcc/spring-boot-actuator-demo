package com.kcc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kcc.utils.CommonUtils;

@RestController
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	@GetMapping("/getStockDetail/{code}")
	public List<String> getStockDetail(@PathVariable String code) {
		String url = "https://goodinfo.tw/StockInfo/StockDetail.asp?STOCK_ID=" + code;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements tables = doc.select("table");

			List<String> lines = new ArrayList<>(100);
			for (Element table : tables) {
				String tableClassName = table.className();
				String firstText = table.selectFirst("tr").selectFirst("td").text();
				if ("solid_1_padding_4_2_tbl".equals(tableClassName) && "年/月".equals(firstText)) {
					parseValue(table, lines);
				}

				if ("solid_1_padding_4_0_tbl".equals(tableClassName) && "合 併 獲 利 狀 況 (/)".equals(firstText)) {
					parseValue(table, lines);
				}
			}

			return lines;
		} catch (IOException e) {
			LOGGER.error("ErrorMsg={}", e.getMessage());
			return null;
		}
	}

	private void parseValue(Element table, List<String> lines) {
		Elements trs = table.select("tr");
		for (Element tr : trs) {
			Elements tds = tr.select("td");
			String firstTd = tr.selectFirst("td").text();
			firstTd = firstTd.replace("(累季)", "");
			if (CommonUtils.isChinese(firstTd)) {
				continue;
			}
			StringBuilder line = new StringBuilder(30);
			for (Element td : tds) {
				line.append(td.text()).append(" ");
			}
			lines.add(line.toString().trim());
		}
		lines.add(" ");
	}
}
