package org.qza.gft.crawler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author qza
 * 
 * Configures GFT Crawler
 *
 */
@Configuration
@ComponentScan(basePackages={"org.qza.gft.crawler"})
@PropertySource("classpath:gft-crawler.properties")
public class CrawlerConfig {

}
