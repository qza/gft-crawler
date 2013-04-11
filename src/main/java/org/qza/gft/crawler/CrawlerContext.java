package org.qza.gft.crawler;

import java.util.Date;

/**
 * @author qza
 * 
 *         Context with queues for spawning crawlers
 * 
 */
public class CrawlerContext {

	private CrawlerQueue workQueue = new CrawlerQueue();

	private Date startTime;
	private Date endTime;

	private String linksCss;

	private String resultsfile;
	private String reportsfile;

	private Integer releaseTime;

	public CrawlerQueue getWorkQueue() {
		return workQueue;
	}

	public void setWorkQueue(CrawlerQueue workQueue) {
		this.workQueue = workQueue;
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

	public String getLinksCss() {
		return linksCss;
	}

	public void setLinksCss(String linksCss) {
		this.linksCss = linksCss;
	}

	public String getResultsfile() {
		return resultsfile;
	}

	public void setResultsfile(String resultsfile) {
		this.resultsfile = resultsfile;
	}

	public Integer getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Integer releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getReportsfile() {
		return reportsfile;
	}

	public void setReportsfile(String reportsfile) {
		this.reportsfile = reportsfile;
	}

}
