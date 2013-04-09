package org.qza.gft.crawler.worker.impl;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.qza.gft.crawler.CrawlerContext;
import org.qza.gft.crawler.CrawlerProperties;
import org.qza.gft.crawler.worker.CrawlerWorkerBase;
import org.qza.gft.crawler.worker.CrawlerWorkerException;

/**
 * @author qza
 * 
 */
public class JsoupWorker extends CrawlerWorkerBase {

	public JsoupWorker(String crawlerName, final CrawlerContext context,
			final CrawlerProperties props) {
		super(crawlerName, context, props);
	}

	public void execute(String link) throws CrawlerWorkerException {
		Document doc = null;
		try {
			doc = Jsoup.connect(link)
					.timeout(props.getJsoupTimeout()).get();
			Iterator<Element> links = doc.select(props.getLinksCss())
					.iterator();
			while (links.hasNext()) {
				String relatedLink = links.next().attr("href");
				context.addLink(relatedLink);
			}
		} catch (Throwable ex) {
			throw new CrawlerWorkerException(ex.getMessage());
		}
	}

}
