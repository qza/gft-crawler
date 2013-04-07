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

	private String linksCss;
	
	private Boolean wait4queue;
	
	private Integer maxResults;
	
	private String resultsfile;
	private String reportsfile;

	private Integer crawlerCount;
	private Integer initPause;
	private Integer releaseTime;
	
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
	
	public Boolean getWait4queue() {
		return wait4queue;
	}
	
	public void setWait4queue(Boolean wait4queue) {
		this.wait4queue = wait4queue;
	}
	
	public Integer getMaxResults() {
		return maxResults;
	}
	
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
	public String getResultsfile() {
		return resultsfile;
	}
	
	public void setResultsfile(String resultsfile) {
		this.resultsfile = resultsfile;
	}
	
	public Integer getCrawlerCount() {
		return crawlerCount;
	}
	
	public void setCrawlerCount(Integer crawlerCount) {
		this.crawlerCount = crawlerCount;
	}
	
	public Integer getReleaseTime() {
		return releaseTime;
	}
	
	public void setReleaseTime(Integer releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	public Integer getInitPause() {
		return initPause;
	}
	
	public void setInitPause(Integer initPause) {
		this.initPause = initPause;
	}
	
	public String getReportsfile() {
		return reportsfile;
	}
	
	public void setReportsfile(String reportsfile) {
		this.reportsfile = reportsfile;
	}

}
