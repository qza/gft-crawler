package org.qza.gft.crawler.worker;

import org.qza.gft.crawler.CrawlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 *
 * Base for all crawler workers.
 *
 */
public abstract class CrawlerWorkerBase implements CrawlerWorker {

	final Logger log;
	final CrawlerContext context;

	public CrawlerWorkerBase(String crawlerName, final CrawlerContext context) {
		this.log = LoggerFactory.getLogger(crawlerName);
		this.context = context;
	}

	public void run() {
		try {
			if (context.getQueuedLinks().peek() == null) {
				log.warn("Nothing to do here");
			} else {
				execute();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
			// TODO Check if should throw runtime exception
		}
	}

}
