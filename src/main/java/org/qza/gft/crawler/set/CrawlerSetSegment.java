package org.qza.gft.crawler.set;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class CrawlerSetSegment {

	private final String segmentId;

	private final Set<String> set;

	private final Integer segmentSize;

	private final AtomicInteger calls = new AtomicInteger(0);

	private final AtomicInteger founds = new AtomicInteger(0);
	
	public CrawlerSetSegment(final Integer segmentId,
			final Integer segmentSize) {
		this.segmentSize = segmentSize;
		this.set = new LinkedHashSet<String>(segmentSize);
		this.segmentId = String.format("Segment <%d>", segmentId);
	}

	public Boolean add(String element) {
		if (!contains(element) && set.size() < segmentSize) {
			return set.add(element);
		}
		return false;
	}

	public Boolean contains(String element) {
		Boolean contains = false;
		contains = set.contains(element);
		if (contains)
			founds.incrementAndGet();
		calls.incrementAndGet();
		return contains;
	}

	public String getStats() {
		return String.format(
				" %s \t:::\t size: %d \t:::\t calls: %d \t:::\t found: %d \t:::\t ",
				segmentId, set.size(), calls.get(), founds.get());
	}

	public Integer size() {
		return set.size();
	}

	public Set<String> data() {
		return set;
	}

}
