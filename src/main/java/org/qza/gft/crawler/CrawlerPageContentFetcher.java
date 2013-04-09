package org.qza.gft.crawler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

public class CrawlerPageContentFetcher {

	private static final int MAX_CONNECTIONS_PER_ROUTE = 50;
	private static HttpClient httpClient;

	public InputStream getPageContent(final String pageUrl) throws ClientProtocolException, IOException {
		final HttpResponse response = doHttpGet(pageUrl);
		final StatusLine status = response.getStatusLine();

		if (isHttpStatusOk(status)) {
			final HttpEntity httpEntity = response.getEntity();
			return httpEntity.getContent();
		}

		throw new UnsupportedOperationException("Page unavailable; status line: " + status);
	}

	private HttpResponse doHttpGet(final String pageUrl) throws IOException, ClientProtocolException {
		final HttpGet httpGet = new HttpGet(pageUrl);
		final HttpResponse response = getHttpClient().execute(httpGet);
		return response;
	}

	private static synchronized HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new DefaultHttpClient(new GiftlyConnectionManager(MAX_CONNECTIONS_PER_ROUTE));
		}
		return httpClient;

	}

	private boolean isHttpStatusOk(final StatusLine status) {
		return HttpStatus.SC_OK == status.getStatusCode();
	}

	private static class GiftlyConnectionManager extends PoolingClientConnectionManager {
		public GiftlyConnectionManager(final int defaultMaxPerRoute) {
			setDefaultMaxPerRoute(defaultMaxPerRoute);
		}
	}

}
