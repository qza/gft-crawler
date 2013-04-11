package org.qza.gft.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author qza
 *
 */
public class CrawlerResulter {

	private final String resultsFilePath;

	private final CrawlerWorkQueue workQueue;

	public CrawlerResulter(String resultsFilePath, CrawlerWorkQueue workQueue) {
		this.resultsFilePath = resultsFilePath;
		this.workQueue = workQueue;
	}


	/**
	 * Writes visited link from context to file
	 */
	public void writeLinksToFile() {
		FileWriter writer;
		try {
			File file = new File(resultsFilePath);
			writer = new FileWriter(file);
			Iterator<String> it = workQueue.getVisitedLinks().iterator();
			while (it.hasNext()) {
				String lnk = it.next();
				writer.write(lnk + "\r\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
