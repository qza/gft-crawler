package org.qza.gft.crawler.worker.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.qza.gft.crawler.CrawlerContext;
import org.qza.gft.crawler.worker.CrawlerWorkerBase;
import org.qza.gft.crawler.worker.CrawlerWorkerException;

/**
 * @author qza
 *
 */
public class JsoupWorker extends CrawlerWorkerBase {
	private final String linkSelector;

	public JsoupWorker(String crawlerName, final CrawlerContext context) {
		super(crawlerName, context);
		linkSelector = context.getLinksCss();
	}

	public void execute() throws CrawlerWorkerException {
		try {
			final String pageUrl = getWorkQueue().take();
			final Collection<String> links = extractLinksFromPageUsingJsoup(pageUrl);
			for(String link: links)
				getWorkQueue().addAndVisitIfNotVisited(link);
		} catch (Throwable ex) {
			throw new CrawlerWorkerException(ex.getMessage());
		}
	}

	private Collection<String> extractLinksFromPageUsingJsoup(String pageUrl) throws ClientProtocolException, IOException {
		final Document document = Jsoup.connect(pageUrl).timeout(0).get();
		return extractLinksFromDocument(document);
	}

	private Collection<String> extractLinksFromDocument(final Document document) {
		final Collection<String> links = new ArrayList<String>();
		final Elements linkElements = document.select(linkSelector);
		for (Element link : linkElements)
			links.add(link.attr("href"));
		return links;
	}
}
