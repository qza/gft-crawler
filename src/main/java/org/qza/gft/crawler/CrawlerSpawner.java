package org.qza.gft.crawler;

import java.util.concurrent.ThreadPoolExecutor;

public class CrawlerSpawner {
	
	final CrawlerContext context;
	final ThreadPoolExecutor executor;

	public CrawlerSpawner(final CrawlerContext context, final ThreadPoolExecutor executor) {
		this.context = context; 
		this.executor = executor;
	}

}
