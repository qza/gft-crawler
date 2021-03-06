package org.qza.gft.crawler.worker;

import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.qza.gft.crawler.CrawlerContext;
import org.qza.gft.crawler.CrawlerProperties;
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
	final protected CrawlerProperties props;

	public CrawlerWorkerBase(String crawlerName, final CrawlerContext context,
			final CrawlerProperties props) {
		this.log = LoggerFactory.getLogger(crawlerName);
		this.context = context;
		this.props = props;
	}

	public void run() {
		try {
			String link = getQueued().poll();
			if (link == null) {
				log.warn("Nothing to do here");
			} else {
				try {
					execute(props.getStripPrefix().concat(link));
				} catch (CrawlerWorkerException cwe) {
					// logError(link, cwe);
				}
				// logSuccess();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
	}

	protected void logSuccess() {
		StringBuilder message = new StringBuilder();
		message.append(String.format(" ~ completed ~ : ~ ( q: %d ! v : %d )",
				context.getQueuedLinks().size(), context.getVisitedLinks()
						.size()));
		log.info(message.toString());
	}

	protected void logError(String link, Exception ex) {
		StringBuilder message = new StringBuilder();
		message.append(String.format(
				"\n - problem with link %s . \n \t Error: %s \n", link,
				ex.getMessage()));
		log.error(message.toString());
	}

	protected BlockingQueue<String> getQueued() {
		return context.getQueuedLinks();
	}

	protected Set<String> getVisited() {
		return context.getVisitedLinks().data();
	}

}
