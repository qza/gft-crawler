package org.qza.gft.crawler;

import java.util.concurrent.ThreadPoolExecutor;

public class CrawlerSpawner {

	final ThreadPoolExecutor executor;

	public CrawlerSpawner(final ThreadPoolExecutor executor) {
		this.executor = executor;
	}

}
