package org.qza.gft.crawler.worker;

import org.qza.gft.crawler.CrawlerWorkQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 *
 *         Base for all crawler workers.
 *
 */
public abstract class CrawlerWorkerBase implements CrawlerWorker {

	protected final Logger log;
	private final CrawlerWorkQueue workQueue;
	private String currentLink;

	public CrawlerWorkerBase(String crawlerName, final CrawlerWorkQueue workQueue) {
		this.log = LoggerFactory.getLogger(crawlerName);
		this.workQueue = workQueue;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted())
			processSingleLink();

		logSuccess();
	}

	private void processSingleLink() {
		try {
			currentLink = getWorkQueue().take();
			execute();
		} catch (CrawlerWorkerException cwe) {
			getWorkQueue().add(currentLink);
			logError(currentLink, cwe);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	protected void logSuccess() {
		StringBuilder message = new StringBuilder();
		message.append(String.format("completed: (q : %d) (v : %d)", workQueue.getSize(), workQueue.getVisitedSize()));
		log.info(message.toString());
	}

	protected void logError(String link, Exception ex) {
		StringBuilder message = new StringBuilder();
		message.append(String.format("\n - problem with link %s . \n \t Error: %s \n", link, ex.getMessage()));
		log.error(message.toString(), ex);
	}

	protected CrawlerWorkQueue getWorkQueue() {
		return workQueue;
	}

	protected String getCurrentLink() {
		return currentLink;
	}

}
