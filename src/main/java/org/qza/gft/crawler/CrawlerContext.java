package org.qza.gft.crawler;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.qza.gft.crawler.set.CrawlerSet;

/**
 * @author qza
 * 
 *         Context with queues for spawning crawlers
 * 
 */
public class CrawlerContext {

	private final CrawlerSet visitedLinks;

	private final BlockingQueue<String> backedQueue;
	
	private Date startTime;

	private Date endTime;

	public CrawlerContext(final CrawlerSet visitedLinks) {
		this.visitedLinks = visitedLinks;
		this.backedQueue = new LinkedBlockingQueue<String>();
	}

	public BlockingQueue<String> getQueuedLinks() {
		return visitedLinks.getQueue();
	}

	public CrawlerSet getVisitedLinks() {
		return visitedLinks;
	}

	public void addLink(String link) {
		visitedLinks.add(link);
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public BlockingQueue<String> getBackedQueue() {
		return backedQueue;
	}
	
	public void activateBackQueued(){
		visitedLinks.getQueue().addAll(backedQueue);
		backedQueue.clear();
	}

}
