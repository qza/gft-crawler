package org.qza.gft.crawler.worker;

/**
 * @author qza
 * 
 */
public interface CrawlerWorker extends Runnable {

	/**
	 * Process link and update crawling context
	 * 
	 * @param context
	 * @throws CrawlerWorkerException
	 */
	void execute(String link) throws CrawlerWorkerException;

}
