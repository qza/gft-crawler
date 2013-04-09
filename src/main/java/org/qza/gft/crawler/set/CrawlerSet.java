package org.qza.gft.crawler.set;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.qza.gft.crawler.CrawlerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qza
 * 
 */
public class CrawlerSet {

	private final String stripPrefix;

	private final Integer segmentSize;

	private final Integer segmentCount;

	private final BlockingQueue<String> queue;

	private final CrawlerSetSegment[] segments;

	private final AtomicInteger activeSegment = new AtomicInteger(0);

	private final Logger log = LoggerFactory.getLogger(CrawlerSet.class);

	public CrawlerSet(final CrawlerProperties props,
			final BlockingQueue<String> queue) {
		this.segmentCount = props.getSegmentCount();
		this.segmentSize = props.getMaxResults() / this.segmentCount;
		this.segments = new CrawlerSetSegment[this.segmentCount];
		for (int i = 0; i < segmentCount; i++) {
			segments[i] = new CrawlerSetSegment(i + 1, this.segmentSize);
		}
		this.queue = queue;
		this.stripPrefix = props.isStripLinks() ? props.getStripPrefix() : null;
	}

	public Boolean add(String element) {
		if (stripPrefix != null) {
			element = element.replaceFirst(stripPrefix, "");
		}
		if (!contains(element)) {
			boolean added = false;
			while (!added) {
				CrawlerSetSegment segment = segments[activeSegment.get()];
				synchronized (this) {
					added = segment.add(element);
					if (added) {
						queue.add(element);
					} else {
						log.info("\n\n\nMoving to segment: "
								+ (activeSegment.incrementAndGet() + 1)
								+ "\n\n");
					}
				}
			}
			return true;
		}
		return false;
	}

	public Boolean contains(String element) {
		for (int i = 0; i <= activeSegment.get(); i++) {
			if (segments[i].contains(element))
				return true;
		}
		return false;
	}

	public String stats() {
		StringBuilder stats = new StringBuilder();
		for (CrawlerSetSegment segment : segments)
			stats.append("\r\t" + segment.getStats() + "\r\n");
		return stats.toString();
	}

	public Integer size() {
		Integer size = 0;
		for (CrawlerSetSegment s : segments) {
			size += s.size();
		}
		return size;
	}

	public Set<String> data() {
		Set<String> data = new HashSet<String>(size());
		for (CrawlerSetSegment s : segments) {
			data.addAll(s.data());
		}
		return data;
	}

	public BlockingQueue<String> getQueue() {
		return queue;
	}

}
