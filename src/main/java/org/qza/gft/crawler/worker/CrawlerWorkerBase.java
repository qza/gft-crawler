package org.qza.gft.crawler.worker;

import org.qza.gft.crawler.CrawlerContext;
import org.qza.gft.crawler.CrawlerQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 *
 *         Base for all crawler workers.
 *
 */
public abstract class CrawlerWorkerBase implements CrawlerWorker {

	final protected Logger log;
	final protected CrawlerContext context;

	public CrawlerWorkerBase(String crawlerName, final CrawlerContext context) {
		this.log = LoggerFactory.getLogger(crawlerName);
		this.context = context;
	}

	public void run() {
		while (!Thread.interrupted()) {
			processSingleLink();
		}
	}

	private void processSingleLink() {
		String link = getWorkQueue().peek();

		try {
			execute();
		} catch (CrawlerWorkerException cwe) {
			getWorkQueue().add(link);
			logError(link, cwe);
		}

		logSuccess();
	}

	protected void logSuccess() {
		final CrawlerQueue workQueue = context.getWorkQueue();

		StringBuilder message = new StringBuilder();
		message.append(String.format(" ~ completed ~ : ~ ( q : %d ) ( v : %d )", workQueue.getSize(),
				workQueue.getVisitedSize()));
		log.info(message.toString());
	}

	protected void logError(String link, Exception ex) {
		StringBuilder message = new StringBuilder();
		message.append(String.format("\n - problem with link %s . \n \t Error: %s \n", link, ex.getMessage()));
		log.error(message.toString(), ex);
	}

	protected CrawlerQueue getWorkQueue() {
		return context.getWorkQueue();
	}

}
