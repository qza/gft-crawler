package org.qza.gft.crawler;

import static java.lang.String.format;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.qza.gft.crawler.worker.CrawlerWorkerBase;
import org.qza.gft.crawler.worker.impl.AmazonJsoupWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 *
 *         Spawns multiple crawler workers with pool executor
 *
 */
public class CrawlerSpawner {

	private final Logger log;

	private final CrawlerContext context;
	private final ThreadPoolExecutor executor;

	private Date startTime;
	private Date endTime;

	private final CrawlerWorkQueue workQueue = new CrawlerWorkQueue();

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
		workQueue.add(context.getEntryUrl());
		startTime = new Date(System.currentTimeMillis());

		final AtomicInteger crawlerId = new AtomicInteger();
		final int threadCount = Runtime.getRuntime().availableProcessors() * 15;

		for (int i = 0; i < threadCount; i++) {
			final String workerName = format("Giftly crawler %d", crawlerId.getAndIncrement());
			CrawlerWorkerBase worker = new AmazonJsoupWorker(workerName, workQueue);
			executor.execute(worker);
		}

		log.info(format("All %d crawlers started", threadCount));
	}

	private void waitToFinish() {
		while (isNotResultMax()) {
			zzz(1000);
		}

	}

	protected boolean isNotResultMax() {
		return context.getMaxResults() >= workQueue.getVisitedSize();
	}

	protected void shutdown() {
		endTime = new Date(System.currentTimeMillis());

		try {
			executor.shutdownNow();
			executor.awaitTermination(context.getReleaseTime(), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// ignore
		}

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

	public CrawlerWorkQueue getWorkQueue() {
		return workQueue;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
