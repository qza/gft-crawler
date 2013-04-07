package org.qza.gft.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author qza
 * 
 */
public class CrawlerReporter {

	final CrawlerContext context;
	final CrawlerSpawner spawner;

	public CrawlerReporter(final CrawlerContext context,
			final CrawlerSpawner spawner) {
		this.context = context;
		this.spawner = spawner;
	}

	/**
	 * Writes crawling report
	 */
	public void writeReport() {
		FileWriter writer;
		try {
			File file = new File(context.getReportsfile());
			writer = new FileWriter(file);
			Date startTime = context.getStartTime();
			Date endTime = context.getEndTime();
			Integer visitedSize = context.getVisitedLinks().size();
			String duration = calculateDuration(startTime, endTime);
			Integer visitedInSecond = calculateVisitedInSecond(visitedSize, startTime, endTime);
			String report = String.format(getTemplate(),
					context.getCrawlerCount(), context.getInitPause(),
					context.getWait4queue(), spawner.getCorePoolSize(),
					spawner.getMaximumPoolSize(), context.getReleaseTime(),
					duration, visitedSize, visitedInSecond,
					context.getQueuedLinks().size(), spawner.getTaskCount(),
					spawner.getCompletedTaskCount(), spawner.getActiveCount());
			writer.write(report + "\r\n");
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTemplate() {
		StringBuilder template = new StringBuilder();
		template.append("\r\n");
		template.append(" \t\t Crawling report \t \r\n");
		template.append(" ********************************** \r\n");
		template.append("\r\n");
		template.append(" Parameters: \t \r\n");
		template.append(" \t Runner count: \t\t\t\t %d \r\n");
		template.append(" \t Runner init pause: \t\t %d \r\n");
		template.append(" \t Wait 4 queue: \t\t\t\t %b \r\n");
		template.append(" \t Pool initialSize: \t\t\t %d \r\n");
		template.append(" \t Pool maxSize: \t\t\t\t %d \r\n");
		template.append(" \t Release time: \t\t\t\t %d \r\n");
		template.append("\r\n");
		template.append(" Results: \t \r\n");
		template.append(" \t Duration: \t\t\t\t\t %s \r\n");
		template.append(" \t Visited: \t\t\t\t\t %d \r\n");
		template.append(" \t Visited / Second: \t\t\t %s \r\n");
		template.append(" \t Remained in queue: \t\t %d \r\n");
		template.append(" \t Executor task count: \t\t %d \r\n");
		template.append(" \t Completed task count: \t\t %d \r\n");
		template.append(" \t Remained task count: \t\t %d \r\n");
		template.append("\r\n");
		template.append(" ********************************** \r\n");
		return template.toString();
	}

	private String calculateDuration(Date start, Date end) {
		long duration = end.getTime() - start.getTime();
		int hours = (int) (duration / (1000 * 60 * 60));
		int minutes = (int) (duration - hours * 1000 * 60 * 60) / (1000 * 60);
		int seconds = (int) (duration - hours * 1000 * 60 * 60 - minutes * 1000 * 60) / 1000;
		return String.format("%d hours %d minutes %d seconds", hours, minutes,
				seconds);
	}

	private Integer calculateVisitedInSecond(Integer visited, Date start,
			Date end) {
		int seconds = (int) (end.getTime() - start.getTime()) / (1000);
		return visited / seconds;
	}

}
