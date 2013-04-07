package org.qza.gft.crawler.worker.impl;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.qza.gft.crawler.CrawlerContext;
import org.qza.gft.crawler.worker.CrawlerWorkerBase;
import org.qza.gft.crawler.worker.CrawlerWorkerException;

/**
 * @author qza
 *
 */
public class JsoupWorker extends CrawlerWorkerBase {

	public JsoupWorker(String crawlerName, final CrawlerContext context) {
		super(crawlerName, context);
	}

	public void execute(String link) throws CrawlerWorkerException {
		Document doc = null;
		try {
			doc = Jsoup.connect(link).timeout(0).get();
			Iterator<Element> links = doc.select(context.getLinksCss()).iterator();
			while (links.hasNext()) {
				String relatedLink = links.next().attr("href");
				if (!getVisited().contains(relatedLink)) {
					getQueued().put(relatedLink);
					getVisited().put(relatedLink);
				}
			}
			
		} catch (Exception ex) {
			logError(link, ex);
		}

	}

}
