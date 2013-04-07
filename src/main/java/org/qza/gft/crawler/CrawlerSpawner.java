package org.qza.gft.crawler;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.qza.gft.crawler.worker.CrawlerWorkerBase;
import org.qza.gft.crawler.worker.impl.JsoupWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 * 
 *         Spawns multiple crawler workers with pool executor
 * 
 */
public class CrawlerSpawner {

	final Logger log;
	final CrawlerContext context;
	final ThreadPoolExecutor executor;

	public CrawlerSpawner(final CrawlerContext context,
			final ThreadPoolExecutor executor) {
		this.context = context;
		this.executor = executor;
		this.log = LoggerFactory.getLogger("Giftly Spawner");
	}

	public void spawn() {
		try {
			begin();
		} finally {
			end();
		}
	}

	protected void begin() {
		AtomicInteger crawlerId = new AtomicInteger();
		for (int i = 0; i < context.getCrawlerCount() && isNotResultMax(); i++) {
			CrawlerWorkerBase worker = new JsoupWorker(String.format(
					"Giftly crawler %d", crawlerId.getAndIncrement()), context);
			if (context.getWait4queue()) {
				while (executor.getActiveCount() > 0
						&& context.getQueuedLinks().size() == 0) {
					log.warn("Waiting for queue");
					zzz(1000);
				}
			}
			executor.execute(worker);
			log.info(String.format("%d started. Active : %d", i,
					executor.getActiveCount()));
			zzz(context.getInitPause());
		}
	}

	protected void end() {
		for (int i = 0; i < context.getReleaseTime(); i++) {
			if (!executor.isTerminated()) {
				log.warn(String.format(
						"\n\n Exiting in %d .. Still active: %d \n\n",
						(context.getReleaseTime() - i),
						executor.getActiveCount()));
				zzz(1000);
			}
		}
		executor.shutdown();
	}

	protected boolean isNotResultMax() {
		return context.getMaxResults() >= context.getVisitedLinks().size();
	}

	protected void zzz(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}

}
