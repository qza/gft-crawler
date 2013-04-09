package org.qza.gft.crawler;

/**
 * @author qza
 * 
 *         Crawler configuration holder
 * 
 */
public class CrawlerProperties {

	private String linksCss;

	private Boolean wait4queue;

	private Integer maxResults;

	private String stripPrefix;

	private String stripSufix;

	private String resultsfile;

	private String reportsfile;

	private Integer crawlerCount;

	private Integer initPause;

	private Integer releaseTime;

	private Integer segmentCount;

	private Boolean persistQueue;

	private String queueFile;

	private Integer maxQueueSize;

	private Integer jsoupTimeout;

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

	public String getStripPrefix() {
		return stripPrefix;
	}

	public String getStripSufix() {
		return stripSufix;
	}

	public void setStripPrefix(String stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public void setStripSufix(String stripSufix) {
		this.stripSufix = stripSufix;
	}

	public Integer getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(Integer segmentCount) {
		this.segmentCount = segmentCount;
	}

	public Integer getMaxQueueSize() {
		return maxQueueSize;
	}

	public Boolean getPersistQueue() {
		return persistQueue;
	}

	public String getQueueFile() {
		return queueFile;
	}

	public void setQueueFile(String queueFile) {
		this.queueFile = queueFile;
	}

	public void setMaxQueueSize(Integer maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
	}

	public void setPersistQueue(Boolean persistQueue) {
		this.persistQueue = persistQueue;
	}

	public Integer getJsoupTimeout() {
		return jsoupTimeout;
	}

	public void setJsoupTimeout(Integer jsoupTimeout) {
		this.jsoupTimeout = jsoupTimeout;
	}
	
	public Boolean isStripLinks(){
		return (getStripPrefix() != null && getStripPrefix().length() > 0);
	}

}
