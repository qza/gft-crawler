/**
 * 
 */
package org.qza.gft.crawler;

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

	private CrawlerResulter resulter;

	private CrawlerReporter reporter;

	final Logger log = LoggerFactory.getLogger(CrawlerRunner.class);

	/**
	 * Created new CrawlerRunner instance
	 */

	public CrawlerRunner() {
		ac = new AnnotationConfigApplicationContext(CrawlerConfig.class);
		spawner = ac.getBean(CrawlerSpawner.class);
		resulter = ac.getBean(CrawlerResulter.class);
		reporter = ac.getBean(CrawlerReporter.class);
	}

	public void start() {
		try {
			log.info("Crawling process started.");
			spawner.spawn();
			log.info("Writing links to file ... ");
			resulter.writeLinksToFile();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			log.info("Writing crawling report ... ");
			reporter.writeReport();
			log.info("Crawling process ended.");
			System.exit(0);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrawlerRunner runner = new CrawlerRunner();
		runner.start();
	}

}
