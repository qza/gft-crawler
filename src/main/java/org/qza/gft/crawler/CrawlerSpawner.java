package org.qza.gft.crawler;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

	public CrawlerSpawner(final CrawlerContext context, final ThreadPoolExecutor executor) {
		this.context = context;
		this.executor = executor;
		this.log = LoggerFactory.getLogger("Giftly Spawner");
	}

	public void spawn() {
		try {
			begin();
			waitToFinish();
		} finally {
			shutdown();
		}
	}

	protected void begin() {

		context.setStartTime(new Date(System.currentTimeMillis()));

		final AtomicInteger crawlerId = new AtomicInteger();
		final int threadCount = executor.getMaximumPoolSize();

		for (int i = 0; i < threadCount; i++) {
			final String workerName = String.format("Giftly crawler %d", crawlerId.getAndIncrement());
			CrawlerWorkerBase worker = new JsoupWorker(workerName, context);
			executor.execute(worker);
			log.info(String.format("%d started. Active : %d", i, executor.getActiveCount()));
		}
	}

	private void waitToFinish() {
		while (isNotResultMax()) {
			zzz(1000);
		}

	}

	protected void shutdown() {
		context.setEndTime(new Date(System.currentTimeMillis()));

		try {
			executor.shutdownNow();
			executor.awaitTermination(context.getReleaseTime(), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// ignore
		}

	}

	protected boolean isNotResultMax() {
		final CrawlerQueue workQueue = context.getWorkQueue();
		return context.getMaxResults() >= workQueue.getVisitedSize();
	}

	protected void zzz(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.interrupted();
		}
	}

	public Integer getActiveCount() {
		return executor.getActiveCount();
	}

	public Integer getTaskCount() {
		return new Long(executor.getTaskCount()).intValue();
	}

	public Integer getCompletedTaskCount() {
		return new Long(executor.getCompletedTaskCount()).intValue();
	}

	public Integer getCorePoolSize() {
		return new Long(executor.getCorePoolSize()).intValue();
	}

	public Integer getMaximumPoolSize() {
		return new Long(executor.getMaximumPoolSize()).intValue();
	}

	public Integer getLargestPoolSize() {
		return new Long(executor.getLargestPoolSize()).intValue();
	}

}
