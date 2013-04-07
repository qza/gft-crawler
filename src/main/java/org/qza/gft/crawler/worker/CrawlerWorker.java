package org.qza.gft.crawler.worker;

/**
 * @author qza
 * 
 */
public interface CrawlerWorker extends Runnable {

	/**
	 * Process link
	 * 
	 * @param context
	 * @throws CrawlerWorkerException
	 */
	void execute(String link) throws CrawlerWorkerException;
	
}
