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

	private final CrawlerContext context;

	public CrawlerResulter(final CrawlerContext context) {
		this.context = context;
	}

	/**
	 * Writes visited link from context to file
	 */
	public void writeLinksToFile() {
		FileWriter writer;
		try {
			File file = new File(context.getResultsfile());
			writer = new FileWriter(file);
			Iterator<String> it = context.getVisitedLinks().iterator();
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
