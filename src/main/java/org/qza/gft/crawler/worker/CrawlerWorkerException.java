/**
 * 
 */
package org.qza.gft.crawler.worker;

/**
 * @author qza
 * 
 */
public class CrawlerWorkerException extends Exception {

	private static final long serialVersionUID = -467123649279542812L;

	/**
	 * @param message
	 */
	public CrawlerWorkerException(String message) {
		super(message);
	}

	/**
	 * @param throwable
	 */
	public CrawlerWorkerException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public CrawlerWorkerException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
