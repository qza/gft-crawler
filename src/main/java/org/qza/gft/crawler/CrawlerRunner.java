/**
 * 
 */
package org.qza.gft.crawler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author qza
 * 
 *         Initiates crawling process
 * 
 */
public class CrawlerRunner {

	private ApplicationContext ac;

	private CrawlerSpawner spawner;

	final Logger log = LoggerFactory.getLogger(CrawlerRunner.class);

	/**
	 * Created new CrawlerRunner instance
	 */

	public CrawlerRunner() {
		ac = new AnnotationConfigApplicationContext(CrawlerConfig.class);
		spawner = ac.getBean(CrawlerSpawner.class);
	}

	public void start() {
		Date startTime = new Date(System.currentTimeMillis());
		log.info("Crawling process started (" + startTime + ")");
		spawner.spawn();
		Date endTime = new Date(System.currentTimeMillis());
		log.info("Crawling process ended (" + endTime + ")");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrawlerRunner runner = new CrawlerRunner();
		runner.start();
		System.exit(1);
	}

}
