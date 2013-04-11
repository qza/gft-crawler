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

	public CrawlerReporter(final CrawlerContext context, final CrawlerSpawner spawner) {
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
			Integer visitedSize = context.getWorkQueue().getVisitedSize();
			String durationUnit = "Min.";
			Integer duration = calculateDurationInMinutes(startTime, endTime);
			if (duration == 0) {
				durationUnit = "Sec.";
				duration = calculateDurationInSeconds(startTime, endTime);
			}
			Integer visitedInMinute = calculateVisitedInMinute(visitedSize, duration);
			String report = String.format(getTemplate(), spawner.getCorePoolSize(), spawner.getMaximumPoolSize(),
					context.getReleaseTime(), duration, durationUnit, visitedSize, durationUnit, visitedInMinute,
					context.getWorkQueue().getSize(), spawner.getTaskCount(), spawner.getCompletedTaskCount(),
					spawner.getActiveCount());
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
		template.append(" \t Pool initialSize: \t\t\t %d \r\n");
		template.append(" \t Pool maxSize: \t\t\t\t %d \r\n");
		template.append(" \t Release time: \t\t\t\t %d \r\n");
		template.append("\r\n");
		template.append(" Results: \t \r\n");
		template.append(" \t Duration: \t\t\t\t\t %d %s \r\n");
		template.append(" \t Visited: \t\t\t\t\t %d \r\n");
		template.append(" \t Visited / %s: \t\t\t %s \r\n");
		template.append(" \t Remained in queue: \t\t %d \r\n");
		template.append(" \t Executor task count: \t\t %d \r\n");
		template.append(" \t Completed task count: \t\t %d \r\n");
		template.append(" \t Remained task count: \t\t %d \r\n");
		template.append("\r\n");
		template.append(" ********************************** \r\n");
		return template.toString();
	}

	private Integer calculateDurationInMinutes(Date start, Date end) {
		int minutes = (int) ((end.getTime() - start.getTime()) / (1000 * 60));
		return minutes;
	}

	private Integer calculateDurationInSeconds(Date start, Date end) {
		int seconds = (int) ((end.getTime() - start.getTime()) / 1000);
		return seconds;
	}

	private Integer calculateVisitedInMinute(Integer visited, Integer duration) {
		return visited / duration;
	}

}
