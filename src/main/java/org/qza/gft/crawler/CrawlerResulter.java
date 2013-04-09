package org.qza.gft.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author qza
 * 
 */
public class CrawlerResulter {

	private final CrawlerContext context;
	private final CrawlerProperties props;

	public CrawlerResulter(final CrawlerContext context,
			final CrawlerProperties props) {
		this.context = context;
		this.props = props;
	}

	/**
	 * Writes visited link (and queued optionally) from the context to the file
	 */
	public void writeLinksToFile() {
		writeDataToFile(context.getVisitedLinks().data(),
				props.getResultsfile());
		if (props.getPersistQueue() == Boolean.TRUE) {
			writeDataToFile(context.getQueuedLinks(), props.getQueueFile());
		}
	}

	private void writeDataToFile(Collection<String> data, String fileName) {
		FileWriter writer;
		try {
			File file = new File(fileName);
			writer = new FileWriter(file);
			Iterator<String> it = data.iterator();
			while (it.hasNext()) {
				String lnk = it.next();
				writer.write(props.getStripPrefix() + lnk + "\r\n");
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
