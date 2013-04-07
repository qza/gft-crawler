package org.qza.gft.crawler.worker;

/**
 * @author qza
 * 
 */
public interface CrawlerWorker extends Runnable {

	/**
	 * Process queues from the crawling context
	 * 
	 * @param context
	 * @throws CrawlerWorkerException
	 */
	void execute() throws CrawlerWorkerException;
	
}
