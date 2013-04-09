package org.qza.gft.crawler;

import java.util.Date;
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
	final CrawlerProperties props;
	final ThreadPoolExecutor executor;

	public CrawlerSpawner(final CrawlerContext context,
			final CrawlerProperties props, final ThreadPoolExecutor executor) {
		this.context = context;
		this.props = props;
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
		activateQueueBacker();
		context.setStartTime(new Date(System.currentTimeMillis()));
		AtomicInteger crawlerId = new AtomicInteger();
		for (int i = 0; i < props.getCrawlerCount() && isNotResultMax(); i++) {
			CrawlerWorkerBase worker = new JsoupWorker(String.format(
					"Giftly crawler %d", crawlerId.getAndIncrement()), context,
					props);
			if (props.getWait4queue()) {
				while (executor.getActiveCount() > 0
						&& context.getQueuedLinks().size() == 0) {
					log.warn("Waiting for queue");
					zzz(1000);
				}
			}
			executor.execute(worker);
//			log.info(String.format("%d started. Active : %d", i,
//					executor.getActiveCount()));
			zzz(props.getInitPause());
		}
		context.setEndTime(new Date(System.currentTimeMillis()));
	}

	protected void end() {
		for (int i = 0; i < props.getReleaseTime(); i++) {
			if (!executor.isTerminated()) {
				log.warn(String.format(
						"\n\n Exiting in %d .. Still active: %d \n\n",
						(props.getReleaseTime() - i), executor.getActiveCount()));
				zzz(1000);
			}
		}
		context.activateBackQueued();
		executor.shutdown();
	}

	protected void activateQueueBacker() {
		if (props.getMaxQueueSize() != null && props.getMaxQueueSize() > 0) {
			Runnable clener = new Runnable() {
				public void run() {
					while(true) {
						int maxsize = props.getMaxQueueSize();
						if(context.getQueuedLinks().size() >  maxsize ) {
							context.getQueuedLinks().drainTo(context.getBackedQueue(), (maxsize * 90 / 100));
							log.info("\n\nDrained queue\n\n");
						}
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							Thread.interrupted();
						}
					}
				}
			};
			executor.execute(clener);
			log.info("\nQueue backer activated.\n");
		}
	}

	protected boolean isNotResultMax() {
		return props.getMaxResults() > context.getVisitedLinks().size();
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
