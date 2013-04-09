package org.qza.gft.crawler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.qza.gft.crawler.set.CrawlerSet;
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
	public CrawlerProperties properties() {
		CrawlerProperties props = new CrawlerProperties();
		props.setLinksCss(env.getProperty("crawler.links.css"));
		props.setWait4queue(getBooleanProperty("spawner.wait4queue"));
		props.setMaxResults(getIntegerProperty("crawler.maxresults"));
		props.setResultsfile(env.getProperty("crawler.resultsfile"));
		props.setReportsfile(env.getProperty("crawler.reportsfile"));
		props.setCrawlerCount(getIntegerProperty("crawler.count"));
		props.setReleaseTime(getIntegerProperty("spawner.releasetime"));
		props.setInitPause(getIntegerProperty("spawner.initpause"));
		props.setStripPrefix(env.getProperty("crawler.strip.prefix"));
		props.setStripSufix(env.getProperty("crawler.strip.sufix"));
		props.setSegmentCount(getIntegerProperty("spawner.segment.count"));
		props.setMaxQueueSize(getIntegerProperty("crawler.queue.maxsize"));
		props.setPersistQueue(getBooleanProperty("crawler.queue.persist"));
		props.setQueueFile(env.getProperty("crawler.queuefile"));
		props.setJsoupTimeout(getIntegerProperty("crawler.jsoup.timeout"));
		return props;
	}
	
	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public BlockingQueue<String> queue() {
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		return queue;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerSet visitedSet() {
		CrawlerSet visited = new CrawlerSet(properties(), queue());
		return visited;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerContext context() {
		CrawlerContext context = new CrawlerContext(visitedSet());
		String entryPoint = env.getProperty("crawler.entry.url");
		context.getQueuedLinks().add(entryPoint);
		return context;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public ThreadPoolExecutor executor() {
		Integer initSize = getIntegerProperty("spawner.tpool.initsize");
		Integer maxSize = getIntegerProperty("spawner.tpool.maxsize");
		BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<Runnable>();
		ThreadPoolExecutor tpool = new ThreadPoolExecutor(initSize, maxSize,
				10000, TimeUnit.MILLISECONDS, blockingQueue);
		return tpool;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerSpawner spawner() {
		CrawlerSpawner spawner = new CrawlerSpawner(context(), properties(),
				executor());
		return spawner;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerResulter resulter() {
		CrawlerResulter resulter = new CrawlerResulter(context(), properties());
		return resulter;
	}

	@Bean
	@Scope(BeanDefinition.SCOPE_SINGLETON)
	public CrawlerReporter reporter() {
		CrawlerReporter reporter = new CrawlerReporter(context(), properties(),
				spawner());
		return reporter;
	}

	private Integer getIntegerProperty(String key) {
		return Integer.valueOf(env.getProperty(key).trim());
	}

	private Boolean getBooleanProperty(String key) {
		return Boolean.valueOf(env.getProperty(key).trim());
	}

}
