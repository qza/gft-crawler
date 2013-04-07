package org.qza.gft.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author qza
 * 
 *         Configures GFT Crawler
 * 
 */
@Configuration
@ComponentScan(basePackages = { "org.qza.gft.crawler" })
@PropertySource("classpath:gft-crawler.properties")
public class CrawlerConfig {

	@Autowired
	private Environment env;

	@Bean
	public BlockingQueue<Runnable> blockingQueue() {
		return new LinkedBlockingQueue<Runnable>();
	}

	@Bean
	public ThreadPoolExecutor executor() {
		Integer initSize = getIntegerProperty("spawner.tpool.initsize");
		Integer maxSize = getIntegerProperty("spawner.tpool.maxsize");
		ThreadPoolExecutor tpool = new ThreadPoolExecutor(initSize, maxSize,
				0L, TimeUnit.MILLISECONDS, blockingQueue());
		return tpool;
	}

	@Bean
	public CrawlerSpawner spawner() {
		CrawlerSpawner spawner = new CrawlerSpawner(executor());
		return spawner;
	}

	private Integer getIntegerProperty(String key) {
		return Integer.valueOf(env.getProperty(key));
	}

}
