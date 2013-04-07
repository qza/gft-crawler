/**
 * 
 */
package org.qza.gft.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 * 
 * Initiates crawling process
 *
 */
public class CrawlerRunner {
	
	final Logger log = LoggerFactory.getLogger(CrawlerRunner.class);

	/**
	 * Created new CrawlerRunner instance
	 */
	public CrawlerRunner() {
		// TODO Auto-generated constructor stub
	}
	
	public void start() {
		log.info("Crawling process started.");
		// TODO
		log.info("Crawling process ended.");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CrawlerRunner runner = new CrawlerRunner();
		runner.start();
	}

}
