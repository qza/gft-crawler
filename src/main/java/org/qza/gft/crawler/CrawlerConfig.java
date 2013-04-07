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
		context.setEntryUrl(env.getProperty("crawler.entry.url"));
		context.setLinksCss(env.getProperty("crawler.links.css"));
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

}
