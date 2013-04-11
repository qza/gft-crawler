package org.qza.gft.crawler;

/**
 * @author qza
 *
 *         Context with queues for spawning crawlers
 *
 */
public class CrawlerContext {

	private String resultsfile;
	private String reportsfile;

	private String entryUrl;

	private Integer maxResults;
	private Integer releaseTime;

	public String getResultsfile() {
		return resultsfile;
	}

	public void setResultsfile(String resultsfile) {
		this.resultsfile = resultsfile;
	}

	public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	public Integer getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Integer releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public String getReportsfile() {
		return reportsfile;
	}

	public void setReportsfile(String reportsfile) {
		this.reportsfile = reportsfile;
	}

}
