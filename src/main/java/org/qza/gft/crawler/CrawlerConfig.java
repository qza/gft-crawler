package org.qza.gft.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerContext context() {
		CrawlerContext context = new CrawlerContext();
		context.setLinksCss(env.getProperty("crawler.links.css"));
		context.setWait4queue(getBooleanProperty("spawner.wait4queue"));
		context.setMaxResults(getIntegerProperty("crawler.maxresults"));
		context.setResultsfile(env.getProperty("crawler.resultsfile"));
		context.setCrawlerCount(getIntegerProperty("crawler.count"));
		context.setReleaseTime(getIntegerProperty("spawner.releasetime"));
		context.setInitPause(getIntegerProperty("spawner.initpause"));
		context.getQueuedLinks().add(env.getProperty("crawler.entry.url"));
		return context;
	}
	
	@Bean
	public ThreadPoolExecutor executor() {
		Integer initSize = getIntegerProperty("spawner.tpool.initsize");
		Integer maxSize = getIntegerProperty("spawner.tpool.maxsize");
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>(); 
		ThreadPoolExecutor tpool = new ThreadPoolExecutor(initSize, maxSize,
				0L, TimeUnit.MILLISECONDS, blockingQueue);
		return tpool;
	}

	@Bean
	public CrawlerSpawner spawner() {
		CrawlerSpawner spawner = new CrawlerSpawner(context(), executor());
		return spawner;
	}

	private Integer getIntegerProperty(String key) {
		return Integer.valueOf(env.getProperty(key));
	}
	
	private Boolean getBooleanProperty(String key) {
		return Boolean.valueOf(env.getProperty(key));
	}

}
