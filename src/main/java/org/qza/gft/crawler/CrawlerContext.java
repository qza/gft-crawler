package org.qza.gft.crawler;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author qza
 * 
 *         Context with queues for spawning crawlers
 * 
 */
public class CrawlerContext {

	private final BlockingQueue<String> queuedLinks;
	private final BlockingQueue<String> visitedLinks;

	private Date startTime;
	private Date endTime;

	private String entryUrl;
	private String linksCss;

	public CrawlerContext() {
		queuedLinks = new LinkedBlockingQueue<String>();
		visitedLinks = new LinkedBlockingQueue<String>();
	}

	public BlockingQueue<String> getQueuedLinks() {
		return queuedLinks;
	}

	public BlockingQueue<String> getVisitedLinks() {
		return visitedLinks;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getEntryUrl() {
		return entryUrl;
	}

	public String getLinksCss() {
		return linksCss;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	public void setLinksCss(String linksCss) {
		this.linksCss = linksCss;
	}

}
